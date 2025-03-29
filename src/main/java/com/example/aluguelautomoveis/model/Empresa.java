package com.example.aluguelautomoveis.model;

import jakarta.persistence.*;

@Entity
@Table(name = "empresas")
@PrimaryKeyJoinColumn(name = "agente_id")
public class Empresa extends Agente{
    
}
