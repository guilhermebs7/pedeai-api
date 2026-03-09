package com.guilherme.pedeai.service;

import com.guilherme.pedeai.DTO.Request.ProdutoRequestDTO;
import com.guilherme.pedeai.DTO.Response.ProdutoResponseDTO;
import com.guilherme.pedeai.model.Produto;
import com.guilherme.pedeai.model.Restaurante;
import com.guilherme.pedeai.repository.ProdutoRepository;
import com.guilherme.pedeai.repository.RestauranteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final RestauranteRepository restauranteRepository;
    //criar pedido
    @Transactional
    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO dto){

        Restaurante restaurante= restauranteRepository.findById(dto.restauranteId())
                .orElseThrow(()-> new RuntimeException("Restaurante não encontrado"));

        Produto produto= new Produto();

        produto.setNome(dto.nome());
        produto.setPreco(dto.preco());
        produto.setRestaurante(restaurante);

        Produto produtoSalvo= produtoRepository.save(produto);
        return  converterParaDTO(produtoSalvo);
    }

    //listar produtos por restaurente
    public List<ProdutoResponseDTO> listarPorRestaurante(Long restauranteId){

        List<Produto> produtos=
                produtoRepository.findByRestauranteId(restauranteId);

        return produtos.stream().map(this::converterParaDTO).toList();
    }

    //buscar produto por ID
    public ProdutoResponseDTO buscarPorId(Long produtoId){
        Produto produto=produtoRepository.findById(produtoId).orElseThrow(()-> new RuntimeException("produto não encontrado"));
        return converterParaDTO(produto);
    }

    //Atualizar Produto
    @Transactional
    public ProdutoResponseDTO atualizarProduto(Long produtoId,
                                               ProdutoRequestDTO dto){

        Produto produto= produtoRepository.findById(produtoId).orElseThrow(()-> new RuntimeException("Produto não encontrado"));

        Produto produtoAtualizado=produtoRepository.save(produto);

        return converterParaDTO(produtoAtualizado);
    }
    //deletar o produto
    @Transactional
    public void  deletarProduto(Long produtoId){
        Produto produto=produtoRepository.findById(produtoId).orElseThrow(()-> new RuntimeException("produto não encontrado"));
        produtoRepository.delete(produto);
    }



    private ProdutoResponseDTO converterParaDTO(Produto produto) {

        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getRestaurante().getNome()
        );

    }
}
