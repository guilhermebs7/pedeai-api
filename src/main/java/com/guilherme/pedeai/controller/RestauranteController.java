package com.guilherme.pedeai.controller;

import com.guilherme.pedeai.DTO.Request.RestauranteRequestDTO;
import com.guilherme.pedeai.DTO.Response.RestauranteResponseDTO;
import com.guilherme.pedeai.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
@Tag(name = "Restaurantes",description = "Endpoints de gerenciamento de restaurante")
public class RestauranteController {
    public final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }
    @Operation(summary = "Criar restaurante")
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> criarRestaurante(@RequestBody RestauranteRequestDTO dto){
        RestauranteResponseDTO restaurante=restauranteService.criarRestaurante(dto);

        return ResponseEntity.ok(restaurante);
    }
    @Operation(summary = "Listar todos os  restaurantes")
    @GetMapping
    public ResponseEntity<List<RestauranteResponseDTO>> listarTodos(){
        List<RestauranteResponseDTO> lista = restauranteService.listarTodos();

        return ResponseEntity.ok(lista);

    }
    @Operation(summary = "Buscar restaurante pelo Id")
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarPorId(@PathVariable Long id){
        RestauranteResponseDTO restaurante= restauranteService.buscarPorId(id);
        return ResponseEntity.ok(restaurante);
    }
    @Operation(summary = "Listar resturantes pelo Id do usuário")
    @PreAuthorize("hasAnyRole('RESTAURANTE','ADMIN')")
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<RestauranteResponseDTO>> listarPorUsuario(@PathVariable Long userId){
        List<RestauranteResponseDTO> lista=restauranteService.listarPorUsuario(userId);

        return ResponseEntity.ok(lista);
    }
    @Operation(summary = "Deletar restaurante pelo Id")
    @PreAuthorize("hasAnyRole('RESTAURANTE','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRestaurante(@PathVariable Long id){
        restauranteService.deletarRestaurante(id);

        return   ResponseEntity.noContent().build();
    }

}
