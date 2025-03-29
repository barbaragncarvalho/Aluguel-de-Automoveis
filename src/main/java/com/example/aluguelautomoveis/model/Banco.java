package com.example.aluguelautomoveis.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bancos")
@PrimaryKeyJoinColumn(name = "agente_id")
public class Banco extends Agente{
    
}
