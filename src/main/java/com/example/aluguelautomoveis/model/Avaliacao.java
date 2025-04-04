package com.example.aluguelautomoveis.model;

import com.example.aluguelautomoveis.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "avaliacoes")
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_analise", nullable = false)
    private Date dataAnalise;

    @Column(nullable = false)
    private String resultado;

    @Column(nullable = true)
    private String observacao;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    public static Avaliacao avaliarPedido(Pedido pedido, String resultado, String observacao) {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setDataAnalise(new Date());
        avaliacao.setResultado(resultado);
        avaliacao.setObservacao(observacao);
        avaliacao.setPedido(pedido);
        return avaliacao;
    }
}
