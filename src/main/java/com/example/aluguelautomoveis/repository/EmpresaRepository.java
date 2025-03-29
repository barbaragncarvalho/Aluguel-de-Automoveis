package com.example.aluguelautomoveis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.aluguelautomoveis.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

}