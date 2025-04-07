package com.example.aluguelautomoveis.controller;

import com.example.aluguelautomoveis.enums.StatusPedido;
import com.example.aluguelautomoveis.model.Avaliacao;
import com.example.aluguelautomoveis.model.Pedido;
import com.example.aluguelautomoveis.repository.AvaliacaoRepository;
import com.example.aluguelautomoveis.repository.PedidoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/avaliacoes")
@Tag(name = "Avaliações", description = "Operações CRUD para avaliações")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoRepository repository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @PostMapping
    @Operation(summary = "Criar avaliação", description = "Cadastra uma nova avaliação vinculada a um pedido")
    @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso")
    public ResponseEntity<Avaliacao> criar(@Valid @RequestBody Avaliacao avaliacao, @RequestParam Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (avaliacao.getResultado().equalsIgnoreCase("APROVADO")) {
            pedido.setStatus(StatusPedido.APROVADO);
        } else {
            pedido.setStatus(StatusPedido.REPROVADO);
        }
        pedidoRepository.save(pedido); 
        avaliacao.setPedido(pedido);
        return new ResponseEntity<>(repository.save(avaliacao), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar avaliações", description = "Lista todas as avaliações")
    @ApiResponse(responseCode = "200", description = "Avaliações listadas com sucesso")
    public ResponseEntity<List<Avaliacao>> listarTodos() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar avaliação por ID", description = "Busca avaliação pelo ID")
    @ApiResponse(responseCode = "200", description = "Avaliação encontrada")
    @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    public ResponseEntity<Avaliacao> buscarPorId(@PathVariable Long id) {
        Optional<Avaliacao> avaliacao = repository.findById(id);
        return avaliacao.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar avaliação", description = "Atualiza os dados de uma avaliação")
    @ApiResponse(responseCode = "200", description = "Avaliação atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    public ResponseEntity<Avaliacao> atualizar(@Valid @PathVariable Long id, @RequestBody Avaliacao atualizada) {
        return repository.findById(id)
                .map(avaliacao -> {
                    avaliacao.setDataAnalise(atualizada.getDataAnalise());
                    avaliacao.setResultado(atualizada.getResultado());
                    avaliacao.setObservacao(atualizada.getObservacao());
                    return new ResponseEntity<>(repository.save(avaliacao), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar avaliação", description = "Remove uma avaliação do banco de dados")
    @ApiResponse(responseCode = "204", description = "Avaliação deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
