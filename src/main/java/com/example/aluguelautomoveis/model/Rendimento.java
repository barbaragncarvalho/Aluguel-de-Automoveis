package com.example.aluguelautomoveis.model;

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
    private Contratante contratante;

    public void setContratante(Contratante contratante) {
        this.contratante = contratante;
    }

    /*public boolean cadastrarRendimento(List<Rendimento> rendimentos, Contratante contratante) {
        if (rendimentos.size() >= MAX_RENDIMENTO) {
            return false;
        }
        this.setContratante(contratante);
        return true;
    }*/
}
