package com.guilherme.pedeai.controller;

import com.guilherme.pedeai.DTO.Request.UserRequestDTO;
import com.guilherme.pedeai.DTO.Response.UserResponseDTO;
import com.guilherme.pedeai.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários",description = "Endpoints de gerenciamento de usuários")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Criar usuários")
    @PostMapping
    public ResponseEntity<UserResponseDTO> criarUsuario(
            @RequestBody UserRequestDTO dto){

        UserResponseDTO user=userService.criarUsuario(dto);

        return ResponseEntity.ok(user);
    }
    @Operation(summary = "Listar usuários",description = "Retorna todos os usuários cadastrados")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listarTodos(){

        List<UserResponseDTO> users= userService.listarUsuarios();

        return ResponseEntity.ok(users);
    }
    @Operation(summary = "Buscar usuário por Id")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> buscarPorId(@PathVariable Long id){

        UserResponseDTO user=userService.buscarPorId(id);

        return ResponseEntity.ok(user);

    }
    @Operation(summary = "Deletar usuário pelo Id")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        userService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
