package com.example.aluguelautomoveis.repository;

import com.example.aluguelautomoveis.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    
}
