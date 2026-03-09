package com.guilherme.pedeai.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

//"Eu utilizei a entidade ItemPedido para garantir o histórico de dados.
// Ela permite registrar a quantidade de cada produto em um pedido e, principalmente, s
// alvar o preço unitário no momento da compra. Assim, se o restaurante alterar o
// preço do produto no futuro, o valor registrado no pedido do cliente
// permanece íntegro e imutável."
@Entity
@Table(name = "item_pedido")
@Data
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private Integer quantidade;

    @Column(name = "preco_unitario")
    private BigDecimal precounitario;
}
