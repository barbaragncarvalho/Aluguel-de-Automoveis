package com.example.aluguelautomoveis.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "automoveis")
public class Automovel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matricula", nullable = false)
    private String matricula;

    @Column(name = "ano", nullable = false)
    private int ano;

    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "placa", nullable = false)
    private String placa;

    @Column(name = "disponivel", nullable = false)
    private boolean disponivel;

    @ManyToOne
    @JoinColumn(name = "proprietario_id", nullable = false)
    private Pessoa proprietario;
}
