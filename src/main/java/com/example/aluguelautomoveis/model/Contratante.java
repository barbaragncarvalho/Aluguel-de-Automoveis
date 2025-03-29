package com.example.aluguelautomoveis.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

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

    /*
     * @ElementCollection
     * private List<Rendimento> rendimentos;
     */

     @OneToMany(
        mappedBy = "contratante", 
        cascade = CascadeType.ALL, 
        orphanRemoval = true
    )
    private List<Rendimento> rendimentos = new ArrayList<>();

    public String cadastrarRendimento(Rendimento rendimento) {
        if(rendimento.cadastrarRendimento(rendimentos, this)){
            rendimentos.add(rendimento);
            return "Rendimento foi cadastrado!";
        }
        return "Rendimento não foi cadastrado!";
    }

}
