package com.guilherme.pedeai.DTO.Response;

import com.guilherme.pedeai.model.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        String nomeRestaurante,
        StatusPedido status,
        BigDecimal total,
        LocalDateTime datacriacao,
        List<ItemPedidoResponseDTO> itens
) {
}
