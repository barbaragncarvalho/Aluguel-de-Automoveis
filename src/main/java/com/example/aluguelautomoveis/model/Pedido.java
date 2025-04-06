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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contratante_id", nullable = false)
    private Contratante contratante;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "automovel_id", nullable = false)
    private Automovel automovel;

    @OneToOne
    @JoinColumn(name = "contrato_id", nullable = true)
    private Contrato contrato;
}
