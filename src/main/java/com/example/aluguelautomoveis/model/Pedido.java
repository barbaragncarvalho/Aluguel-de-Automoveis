package com.example.aluguelautomoveis.model;

import java.util.Date;

import com.example.aluguelautomoveis.enums.StatusPedido;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date data_pedido;

    @Column(nullable = false)
    private StatusPedido status;

    @Column(nullable = false)
    private Contratante contratante;

    @Column(nullable = false)
    private Automovel automovel;

    @Column(nullable = true)
    private Contrato contrato;
}
