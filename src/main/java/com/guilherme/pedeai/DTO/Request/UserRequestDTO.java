package com.guilherme.pedeai.DTO.Request;

public record UserRequestDTO(
        String nome,
        String email,
        String senha
) {
}
