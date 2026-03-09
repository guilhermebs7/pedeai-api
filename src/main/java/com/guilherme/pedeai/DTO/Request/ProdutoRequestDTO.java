package com.guilherme.pedeai.DTO.Request;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        String nome,
        BigDecimal preco,
        Long restauranteId
) {
}
