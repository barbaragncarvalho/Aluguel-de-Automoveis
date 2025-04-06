package com.example.aluguelautomoveis.controller;

import com.example.aluguelautomoveis.enums.StatusPedido;
import com.example.aluguelautomoveis.model.Pedido;
import com.example.aluguelautomoveis.repository.PedidoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Operações CRUD para pedidos")
public class PedidoController {
    @Autowired
    private PedidoRepository repository;

    @PostMapping
    @Operation(summary = "Criar pedido", description = "Cadastra um novo pedido")
    @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso")
    public ResponseEntity<Pedido> criar(@Valid @RequestBody Pedido pedido) {
        pedido.setData_pedido(new Date());
        pedido.setStatus(StatusPedido.EM_ANALISE);

        Pedido pedidoSalvo = repository.save(pedido);
        return new ResponseEntity<>(pedidoSalvo, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar pedidos", description = "Lista todos os pedidos")
    @ApiResponse(responseCode = "200", description = "Pedidos listados com sucesso")
    public ResponseEntity<List<Pedido>> listarTodos() {
        List<Pedido> pedidos = repository.findAll();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Busca pedido pelo ID")
    @ApiResponse(responseCode = "200", description = "Pedido encontrado")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        Optional<Pedido> pedido = repository.findById(id);
        return pedido.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pedido", description = "Atualiza os dados de um pedido")
    @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @Valid @RequestBody Pedido pedidoAtualizado) {
        return repository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(pedidoAtualizado.getStatus());
                    pedido.setAutomovel(pedidoAtualizado.getAutomovel());
                    pedido.setContratante(pedidoAtualizado.getContratante());
                    pedido.setContrato(pedidoAtualizado.getContrato());
                    Pedido pedidoAtual = repository.save(pedido);
                    return new ResponseEntity<>(pedidoAtual, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar pedido", description = "Remove um pedido do banco de dados")
    @ApiResponse(responseCode = "204", description = "Pedido deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
