package com.example.aluguelautomoveis.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "pessoa_id")
public class Cliente extends Pessoa{
    @Column(unique = true, nullable = false)
    private String cpf;
}
