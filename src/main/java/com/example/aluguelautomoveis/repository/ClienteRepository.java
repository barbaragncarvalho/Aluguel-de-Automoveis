package com.example.aluguelautomoveis.repository;

import com.example.aluguelautomoveis.model.Cliente; // Ensure the Cliente class exists in this package or update the package path
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
