package com.example.aluguelautomoveis.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "pessoas")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Cliente.class, name = "cliente"),
        @JsonSubTypes.Type(value = Banco.class, name = "banco"),
        @JsonSubTypes.Type(value = Empresa.class, name = "empresa")
})
public abstract class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Transient
    private String tipo;

    public String getTipo() {
        if (this instanceof Cliente)
            return "cliente";
        if (this instanceof Banco)
            return "banco";
        if (this instanceof Empresa)
            return "empresa";
        return null;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
