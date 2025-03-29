package com.example.aluguelautomoveis.model;

import jakarta.persistence.*;
//import lombok.Data;

//@Data
@Entity
@Table(name = "agentes")
@PrimaryKeyJoinColumn(name = "usuario_id")
public abstract class Agente extends Usuario{
    /*@Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String matricula;

    @Column(nullable = false)
    private String departamento;*/

    @Column(unique = true, nullable = false)
    private String cnpj;
}
