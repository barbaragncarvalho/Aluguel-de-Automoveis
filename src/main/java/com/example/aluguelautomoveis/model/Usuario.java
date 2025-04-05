package com.example.aluguelautomoveis.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuarios")
public abstract class Usuario extends Pessoa{
    @Column(nullable = false)
    private String rua;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private int numero;

    @Column(nullable = true)
    private String opcional;
    
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;
}
