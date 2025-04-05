package com.example.aluguelautomoveis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.aluguelautomoveis.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
