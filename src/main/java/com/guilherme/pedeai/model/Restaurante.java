package com.guilherme.pedeai.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurante")
@Data
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;


    @OneToOne
    @JoinColumn(name = "user_id")  //usar essas anotações juntas sinaliza uma chave estrangeira
    private User user;

    @OneToMany(mappedBy = "restaurante",cascade = CascadeType.ALL)//A lista no Java é um atalho. Ela não cria uma coluna no banco, mas avisa ao Hibernate: "Ei, quando eu pedir os produtos desse restaurante, vá na tabela de produtos e busque todos que tenham o ID dele".
    private List<Produto> produtos=new ArrayList<>();  // o cascade quer dizer que se alguma coisa acontecer com restaurante prodduto tambem é interferido
}

