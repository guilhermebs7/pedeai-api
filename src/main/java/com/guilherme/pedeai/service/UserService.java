package com.guilherme.pedeai.service;

import com.guilherme.pedeai.DTO.Request.UserRequestDTO;
import com.guilherme.pedeai.DTO.Response.UserResponseDTO;
import com.guilherme.pedeai.model.Role;
import com.guilherme.pedeai.model.RoleName;
import com.guilherme.pedeai.model.User;
import com.guilherme.pedeai.repository.RoleRepository;
import com.guilherme.pedeai.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO criarUsuario(UserRequestDTO dto){
        Role roleCliente=roleRepository.findByName(RoleName.ROLE_CLIENTE).orElseThrow(()->new RuntimeException("Role não encontrado"));

        User user=new User();
        user.setNome(dto.nome());
        user.setEmail(dto.email());
        user.setSenha(passwordEncoder.encode(dto.senha()));

        user.getRoles().add(roleCliente);

        User userSalvo=userRepository.save(user);

        return converterParaDTO(userSalvo);
    }
    public List<UserResponseDTO> listarUsuarios(){
        return userRepository.findAll().stream().map(this::converterParaDTO).toList();
    }

    public UserResponseDTO buscarPorId(Long userId){
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("usuário não encontrado"));

        return converterParaDTO(user);
    }
    @Transactional
    public void deletarUsuario(Long userId){
        User user=userRepository.findById(userId).orElseThrow(()-> new RuntimeException("usuário não encontrado"));

        userRepository.delete(user);
    }

    private UserResponseDTO converterParaDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getNome(),
                user.getEmail()
        );
    }
}
