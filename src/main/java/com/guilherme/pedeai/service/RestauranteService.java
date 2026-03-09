package com.guilherme.pedeai.service;

import com.guilherme.pedeai.DTO.Request.RestauranteRequestDTO;
import com.guilherme.pedeai.DTO.Response.RestauranteResponseDTO;
import com.guilherme.pedeai.model.Restaurante;
import com.guilherme.pedeai.model.Role;
import com.guilherme.pedeai.model.RoleName;
import com.guilherme.pedeai.model.User;
import com.guilherme.pedeai.repository.RestauranteRepository;
import com.guilherme.pedeai.repository.RoleRepository;
import com.guilherme.pedeai.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestauranteService {
    private final RestauranteRepository restauranteRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public RestauranteResponseDTO criarRestaurante(RestauranteRequestDTO dto){
        User dono= userRepository.findById(dto.userId())
                .orElseThrow(()-> new RuntimeException("usuário não encontrado"));


        Role roleRestaurante= roleRepository.findByName(RoleName.ROLE_RESTAURANTE).orElseThrow(()-> new RuntimeException("Role não encontrada"));



        dono.getRoles().add(roleRestaurante);
        userRepository.save(dono);

        Restaurante restaurante=new Restaurante();
        restaurante.setNome(dto.nome());
        restaurante.setDescricao(dto.descricao());
        restaurante.setUser(dono);


        Restaurante restauranteSalvo= restauranteRepository.save(restaurante);
        return  converterParaDTO(restauranteSalvo);
    }
    public RestauranteResponseDTO buscarPorId(Long restauranteId){
        Restaurante restaurante= restauranteRepository.findById(restauranteId)
                .orElseThrow(()-> new RuntimeException("Restaurante não encontrado"));

        return converterParaDTO(restaurante);
    }
    public List<RestauranteResponseDTO> listarTodos(){
        return restauranteRepository.findAll().stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public List<RestauranteResponseDTO> listarPorUsuario(Long userId){
        List<Restaurante> restaurantes= restauranteRepository.findByUserId(userId);

        return restaurantes.stream().map(this::converterParaDTO).toList();


    }
    @Transactional
    public RestauranteResponseDTO atualizar(Long restauranteId,
                                            RestauranteRequestDTO dto){
        Restaurante restaurante=restauranteRepository.findById(restauranteId).orElseThrow(()-> new RuntimeException("restaurante não encontrado"));

        restaurante.setNome(dto.nome());
        restaurante.setDescricao(dto.descricao());

        return converterParaDTO(restaurante);
    }
    @Transactional
    public void deletarRestaurante(Long restauranteId){
        Restaurante restaurante=restauranteRepository.findById(restauranteId).orElseThrow(()-> new RuntimeException("restaurante não encontrado"));

        restauranteRepository.delete(restaurante);

    }


    private RestauranteResponseDTO converterParaDTO(Restaurante restaurante) {
        return new RestauranteResponseDTO(
                restaurante.getId(),
                restaurante.getNome(),
                restaurante.getDescricao(),
                restaurante.getUser().getNome()
        );
    }

}
