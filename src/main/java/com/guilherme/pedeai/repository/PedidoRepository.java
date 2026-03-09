package com.guilherme.pedeai.repository;

import com.guilherme.pedeai.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    List<Pedido> findByClienteId(Long id);

    List<Pedido> findByRestauranteId(Long id);
}

