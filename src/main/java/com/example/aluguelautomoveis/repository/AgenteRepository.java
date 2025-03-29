package com.example.aluguelautomoveis.repository;

import com.example.aluguelautomoveis.model.Agente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgenteRepository extends JpaRepository<Agente, Long> {
    
}
