package com.example.aluguelautomoveis.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "contratantes")
public class Contratante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String rg;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String professor;

    @ElementCollection
    private List<String> rendimentos;
}
