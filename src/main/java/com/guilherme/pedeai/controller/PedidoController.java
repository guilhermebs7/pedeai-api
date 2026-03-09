package com.guilherme.pedeai.controller;

import com.guilherme.pedeai.DTO.Request.PedidoRequestDTO;
import com.guilherme.pedeai.DTO.Response.PedidoResponseDTO;
import com.guilherme.pedeai.model.StatusPedido;
import com.guilherme.pedeai.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos",description = "Endpoints de gerenciamento de pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }


    @Operation(summary = "Criar pedido")
    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestBody PedidoRequestDTO dto){
        PedidoResponseDTO pedido=pedidoService.criarPedido(dto);

        return ResponseEntity.ok(pedido);
    }
    @Operation(summary = "Buscar pedido pelo Id do cliente")
    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/cliente")
    public ResponseEntity<List<PedidoResponseDTO>> buscarPedidosDoCliente(){

        List<PedidoResponseDTO> pedidos=pedidoService.buscarPedidosDoCliente();

        return ResponseEntity.ok(pedidos);
    }
    @Operation(summary = "Buscar pedidos pelo Id do restaurante ")
    @PreAuthorize("hasRole('RESTAURANTE')")
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<PedidoResponseDTO>> buscarPedidosRestaurante(@PathVariable Long restauranteId){
        List<PedidoResponseDTO> pedidos=pedidoService.buscarPedidosDoRestaurante(restauranteId);

        return ResponseEntity.ok(pedidos);
    }
    @Operation(summary = "Alterar status do pedido pelo Id")
    @PreAuthorize("hasRole('RESTAURANTE')")
    @PutMapping("/{pedidoId}/status")
    public ResponseEntity<PedidoResponseDTO> alterarStatus(
            @PathVariable Long pedidoId,
            @RequestParam StatusPedido status
    ){
        PedidoResponseDTO response=pedidoService.alterarStatus(pedidoId,status);

        return ResponseEntity.ok(response);

    }
}
