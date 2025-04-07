package com.example.aluguelautomoveis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aluguelautomoveis.model.Automovel;

public interface AutomovelRepository extends JpaRepository<Automovel, Long> {
    List<Automovel> findByProprietarioId(Long proprietarioId);
}