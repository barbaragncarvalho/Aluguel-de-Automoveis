package com.example.aluguelautomoveis.model;

import com.example.aluguelautomoveis.enums.TipoContrato;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "contratos")
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_inicio", nullable = false)
    private Date dataInicio;

    @Column(name = "data_fim", nullable = false)
    private Date dataFim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoContrato tipo;

    @OneToOne(mappedBy = "contrato")
    private Pedido pedido;
}
