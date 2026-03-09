package com.guilherme.pedeai.repository;

import com.guilherme.pedeai.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante,Long> {
    List<Restaurante> findByUserId(Long userId);
}
