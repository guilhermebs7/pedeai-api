package com.guilherme.pedeai.service;

import com.guilherme.pedeai.DTO.Request.ItemPedidoRequestDTO;
import com.guilherme.pedeai.DTO.Request.PedidoRequestDTO;
import com.guilherme.pedeai.DTO.Response.ItemPedidoResponseDTO;
import com.guilherme.pedeai.DTO.Response.PedidoResponseDTO;
import com.guilherme.pedeai.model.*;
import com.guilherme.pedeai.repository.PedidoRepository;
import com.guilherme.pedeai.repository.ProdutoRepository;
import com.guilherme.pedeai.repository.RestauranteRepository;
import com.guilherme.pedeai.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service  // sinaliza ao spring que a classe é um especialista em regra de negocio
@RequiredArgsConstructor//Cria automaticamente um construtor para as variáveis final, permitindo a Injeção de Dependência dos Repositories (Pedido, Produto, Restaurante e User).
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final RestauranteRepository restauranteRepository;
    private final ProdutoRepository produtoRepository;
    private final UserRepository userRepository;

    @Transactional
// famoso "tudo ou nada" ou é feito por completo ou nada acontece ,exemplo: voce clica em finalizar pedido o sistema faz : registra o pedido, cobra o cartao . sem o @transactional o restaurante recebe o pedido , mas voce n pagou prejuizo com o @Transactional como o pagamento falou o spring cancela o registrp do pedido
    public PedidoResponseDTO criarPedido(PedidoRequestDTO dto){

        //1.Busca o "autor" do pedido. Aqui está simulado com ID 1.
        User cliente=getUsuariosimulado();

        //2.tenta achar o restaurante pelo id que veio no DTO, se não achar,interrompe tudo com uma exceção
        Restaurante restaurante= restauranteRepository.findById(dto.restauranteId())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        //3. Instancia o objeto principal (a "capa" do pedido)
        Pedido pedido= new Pedido();

        //4.preenche os dados básicos do pedido.
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setStatus(StatusPedido.CRIADO);  //todo pedido nasce como criado
        pedido.setDataCriacao(LocalDateTime.now());

        //5.cria uma lista vazia para guardar os itens que vamos processar.
        List<ItemPedido> itens= new ArrayList<>();

        //6.Inicia o calculador de preço total em zero.
        BigDecimal total =BigDecimal.ZERO;

        //7.Loop: Para cada item que o usuario enviou no JSON
        for(ItemPedidoRequestDTO itemDTO:dto.itens()) {

            //8. Busca o produto no banco para saber o preço real e se ele existe
            Produto produto = produtoRepository.findById(itemDTO.produtoId()).orElseThrow(() -> new RuntimeException("produto não encontrado" + itemDTO.produtoId()));

            //9.Regra de ouro: verifica se o produto é mesmo desse restaurante.
            //impede que alguem peça uma pizza da 'pizzaria A' dentro de um pedido da ´lanchonete B '
            if (!produto.getRestaurante().getId().equals(restaurante.getId())) {
                throw new RuntimeException("produto não pertence ao restaurante informado");
            }

            //10. cria a entidade ItemPedido ( aligação entre pedido e produto)
            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());

            //11 Fixa o preço: salva o preço atual do produto no item.
            //se o preço do produto mudar amanhã, o valor deste pedido n muda
            item.setPrecounitario(produto.getPreco());

            //11. calculo: subtotal do item = preço * quantidade.
            BigDecimal subtotal = produto.getPreco().multiply(BigDecimal.valueOf(itemDTO.quantidade()));

            //13. soma o subtotal ao valor total do pedido.
            total = total.add(subtotal);

            //14.adiciona o item pronto na lista.
            itens.add(item);
        }

        //15. finaliza o objeto pedido com a lista de itens e o vlor total calculado.
        pedido.setItens(itens);
        pedido.setTotal(total);

        //16 salva o pedido e por cascata salva os itens.
        Pedido pedidosalvo= pedidoRepository.save(pedido);


        //17- converte o objeto do banco para o DTO de saida e envia para o controller.
        return converterParaDTO(pedidosalvo);

    }

    public List<PedidoResponseDTO> buscarPedidosDoCliente(){

        User cliente = getUsuariosimulado();

        List<Pedido> pedidos =pedidoRepository.findByClienteId(cliente.getId());

        return pedidos.stream().map((this::converterParaDTO)).toList();

    }

    //Buscar pedidos do restaurante

    public List<PedidoResponseDTO> buscarPedidosDoRestaurante(Long restauranteId){

        Restaurante restaurante= restauranteRepository.findById(restauranteId).orElseThrow(()-> new RuntimeException("Restaurante não encontrado"));

        List<Pedido> pedidos= pedidoRepository.findByRestauranteId(restaurante.getId());

        return pedidos.stream().map(this::converterParaDTO).toList();
    }

    @Transactional
    public PedidoResponseDTO alterarStatus(Long pedidoId, StatusPedido novoStatus){
        Pedido pedido=pedidoRepository.findById(pedidoId).orElseThrow(()-> new RuntimeException("pedido não encontrado"));

        validarTransicaoStatus(pedido.getStatus(),novoStatus);
        pedido.setStatus(novoStatus);

        return converterParaDTO(pedido);
    }

    private void validarTransicaoStatus(StatusPedido status_atual, StatusPedido novo_status) {
        if(status_atual == StatusPedido.CRIADO && novo_status==StatusPedido.EM_PREPARO)
            return;

        if(status_atual == StatusPedido.EM_PREPARO && novo_status== StatusPedido.ENVIADO)
            return;

        if (status_atual== StatusPedido.ENVIADO && novo_status== StatusPedido.ENTREGUE)
            return;

        if(novo_status==StatusPedido.CANCELADO)
            return;

        throw  new RuntimeException(
                "transição inválida:"+ status_atual+"->"+novo_status);


    }


    private User getUsuariosimulado() {
        return userRepository.findById(1L).orElseThrow(()-> new RuntimeException(
                "Usuário simulado não encontrado.Crie um usuário")
        );
    }

    private PedidoResponseDTO converterParaDTO(Pedido pedido) {

        List<ItemPedidoResponseDTO> itensDTO= pedido.getItens()
                .stream().map(item-> new ItemPedidoResponseDTO(
                        item.getProduto().getNome(),
                        item.getQuantidade(),item.getPrecounitario()
                )).toList();

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getRestaurante().getNome(),
                pedido.getStatus(),
                pedido.getTotal(),
                pedido.getDataCriacao(),
                itensDTO
        );
    }
}
