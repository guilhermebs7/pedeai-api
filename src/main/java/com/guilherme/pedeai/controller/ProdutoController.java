package com.guilherme.pedeai.controller;

import com.guilherme.pedeai.DTO.Request.ProdutoRequestDTO;
import com.guilherme.pedeai.DTO.Response.ProdutoResponseDTO;
import com.guilherme.pedeai.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
@Tag(name = "Produtos",description = "Endpoints de gerenciamento de produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(summary = "Criar produtos")
    @PreAuthorize("hasRole('RESTAURANTE')")
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criarProduto(@RequestBody ProdutoRequestDTO dto){
        ProdutoResponseDTO produto= produtoService.criarProduto(dto);

        return ResponseEntity.ok(produto);
    }
    @Operation(summary = "Buscar produto por Id")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id){
        ProdutoResponseDTO produto=produtoService.buscarPorId(id);

        return ResponseEntity.ok(produto);
    }

    @Operation(summary = "Listar produtos pelo Id do restaurante")
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarPorRestaurante(@PathVariable Long restauranteId){
        List<ProdutoResponseDTO> lista= produtoService.listarPorRestaurante(restauranteId);

        return ResponseEntity.ok(lista);

    }
    @Operation(summary = "Deletar produto pelo Id")
    @PreAuthorize("hasRole('RESTAURANTE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id){
        produtoService.deletarProduto(id);

        return ResponseEntity.noContent().build();
    }

}
