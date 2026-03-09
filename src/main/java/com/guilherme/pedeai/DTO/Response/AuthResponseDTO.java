package com.guilherme.pedeai.DTO.Response;

public record AuthResponseDTO(
        String acessToken,
        String refreshToken
) {
}
