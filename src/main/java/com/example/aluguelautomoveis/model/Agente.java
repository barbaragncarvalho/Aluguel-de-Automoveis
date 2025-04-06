package com.example.aluguelautomoveis.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "agentes")
@PrimaryKeyJoinColumn(name = "usuario_id")
public abstract class Agente extends Usuario {
    @Column(unique = true, nullable = false)
    private String cnpj;
}
