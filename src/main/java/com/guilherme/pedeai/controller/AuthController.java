package com.guilherme.pedeai.controller;

import com.guilherme.pedeai.DTO.Request.AuthRequestDTO;
import com.guilherme.pedeai.DTO.Response.AuthResponseDTO;
import com.guilherme.pedeai.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "login",description = "Endpoint de login")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody AuthRequestDTO dto){
        return authService.login(dto);
    }
}
