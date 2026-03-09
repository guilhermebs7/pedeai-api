package com.guilherme.pedeai.DTO.Response;

public record LoginResponseDTO(
        String login,
        String senha,
        String role
) {
}
