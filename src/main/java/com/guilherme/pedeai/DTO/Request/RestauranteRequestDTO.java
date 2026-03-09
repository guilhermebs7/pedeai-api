package com.guilherme.pedeai.DTO.Request;

public record RestauranteRequestDTO(
        String nome,
        String descricao,
        Long userId
) {
}
