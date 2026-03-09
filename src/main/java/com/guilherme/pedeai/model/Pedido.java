package com.guilherme.pedeai.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedido")
@Data
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id",nullable = false)
    private User cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id",nullable = false)
    private Restaurante restaurante;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @Column(name = "valor_total",nullable = false)
    private BigDecimal total;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;


    @OneToMany(mappedBy = "pedido",cascade =  CascadeType.ALL,orphanRemoval = true)
    private List<ItemPedido> itens;
}

