package com.example.aluguelautomoveis.repository;

import com.example.aluguelautomoveis.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
