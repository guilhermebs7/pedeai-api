package com.guilherme.pedeai.DTO.Request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoRequestDTO (
        @NotNull Long restauranteId,
        @NotEmpty List<ItemPedidoRequestDTO> itens
){
}
