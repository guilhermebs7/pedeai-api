package com.guilherme.pedeai.service;

import com.guilherme.pedeai.DTO.Request.AuthRequestDTO;
import com.guilherme.pedeai.DTO.Response.AuthResponseDTO;
import com.guilherme.pedeai.model.User;
import com.guilherme.pedeai.repository.UserRepository;
import com.guilherme.pedeai.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO login(AuthRequestDTO dto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(),
                        dto.senha()
                )
        );

        User user= userRepository.findByEmail(dto.email()).orElseThrow();

        String accessToken= jwtService.generateAcessToken(user);
        String refreshToken= jwtService.generateRefreshToken(user);

        return  new AuthResponseDTO(accessToken,refreshToken);


    }
    
}
