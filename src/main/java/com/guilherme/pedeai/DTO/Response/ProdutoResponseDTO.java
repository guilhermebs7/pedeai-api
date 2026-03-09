package com.guilherme.pedeai.DTO.Response;

import java.math.BigDecimal;

public record ProdutoResponseDTO(Long id,
                                 String nome,
                                 BigDecimal preco,
                                 String nomeRestaurante) {
}
