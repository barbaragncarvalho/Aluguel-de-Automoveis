package com.example.aluguelautomoveis.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "contratantes")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Contratante extends Usuario {
    @Column(nullable = false)
    private String rg;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String profissao;

    @OneToMany(mappedBy = "contratante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Rendimento> rendimentos = new ArrayList<>();

    public void cadastrarRendimento(Rendimento rendimento) {
        this.rendimentos.add(rendimento);
        rendimento.setContratante(this);
    }

}
