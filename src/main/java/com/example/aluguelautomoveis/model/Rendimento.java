package com.example.aluguelautomoveis.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rendimentos")
public class Rendimento {
    public static final int MAX_RENDIMENTO = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entidade_empregadora", nullable = false)
    private String entidadeEmpregadora;

    @Column(name = "valor_rendimento", nullable = false)
    private float valorRendimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contratante_id", nullable = false)
    @JsonBackReference
    private Contratante contratante;

    public void setContratante(Contratante contratante) {
        this.contratante = contratante;
    }
}
