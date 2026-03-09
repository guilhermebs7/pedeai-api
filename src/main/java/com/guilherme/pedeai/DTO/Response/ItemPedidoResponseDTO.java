package com.guilherme.pedeai.DTO.Response;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
        String nomeProduto,
        Integer quantidade,
        BigDecimal precounitario
) {
}
