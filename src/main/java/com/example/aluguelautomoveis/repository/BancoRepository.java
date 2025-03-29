package com.example.aluguelautomoveis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.aluguelautomoveis.model.Banco;

public interface BancoRepository extends JpaRepository<Banco, Long> {
    
}