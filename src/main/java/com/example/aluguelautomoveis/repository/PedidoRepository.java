package com.example.aluguelautomoveis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.aluguelautomoveis.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByContratanteId(Long clienteId);
}
