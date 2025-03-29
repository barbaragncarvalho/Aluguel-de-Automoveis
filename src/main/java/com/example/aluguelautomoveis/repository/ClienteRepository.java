package com.example.aluguelautomoveis.repository;

import com.example.aluguelautomoveis.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
}
