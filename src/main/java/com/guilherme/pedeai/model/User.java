package com.guilherme.pedeai.model;

import jakarta.persistence.*;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;


    @ManyToMany(fetch = FetchType.EAGER) //usado o ManyToMany porque 1 usuario pode ter vários perfis e um perfil pode pertencer a vários usuários,EAGER significa que toda vez que você busar um usuário no banco, o java vai trazer automaticamente todas as roles dele junto.
    @JoinTable(name = "user_roles",  //cria uma tebela chamada user_roles que faz a relação do id do usuario com o id das roles
            joinColumns = @JoinColumn(name = "user_id"),  //primeira coluna do JOINS que pega o id do usuario
            inverseJoinColumns =@JoinColumn(name = "role_id"))  //a coluna inversa(outro lado da coluna ) que pega o id do roles para saber as roles de um determinado usuário
    //exemplo do fluxo
    //1.vai na tabela users: ache o Joao. o id dele é 1
    //2.vai na tabela user_roles: me mostre toda as linhas onde o user_id é igual a 1.
    //3.coleta os resultados: achei o role_id 10 e o role_id 20.
    //4.vai na tabela roles: quais são os nomes das roles 10 e 20? são ADMIN e cliente
    //5.monta o objeto : ele coloca essas duas Roles dentro do seu Set<Role>
    private Set<Role> roles= new HashSet<>(); //se usa SET em vez de lista para garantir que o usuario não tenha a mesma permissão repetida.

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toSet());
    }

    @Override
    public @Nullable String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

