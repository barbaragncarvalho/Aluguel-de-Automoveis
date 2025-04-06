package com.example.aluguelautomoveis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.aluguelautomoveis.model.Banco;
import com.example.aluguelautomoveis.repository.BancoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bancos")
@Tag(name = "Bancos", description = "Operações CRUD para bancos")
public class BancoController {
    @Autowired
    private BancoRepository repository;

    @PostMapping
    @Operation(summary = "Criar banco", description = "Cadastra um novo banco")
    @ApiResponse(responseCode = "201", description = "banco criado com sucesso")
    public ResponseEntity<Banco> criar(@RequestBody Banco banco) {
        return new ResponseEntity<>(repository.save(banco), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar bancos", description = "Lista todos os bancos")
    @ApiResponse(responseCode = "200", description = "bancos listados com sucesso")
    public ResponseEntity<List<Banco>> listarTodos() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar banco por ID", description = "Busca banco pelo ID")
    @ApiResponse(responseCode = "200", description = "banco encontrado")
    @ApiResponse(responseCode = "404", description = "banco não encontrado")
    public ResponseEntity<Banco> buscarPorId(@PathVariable Long id) {
        Optional<Banco> banco = repository.findById(id);
        return banco.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar banco", description = "Atualiza os dados de um banco")
    @ApiResponse(responseCode = "200", description = "banco atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "banco não encontrado")
    public ResponseEntity<Banco> atualizar(@PathVariable Long id, @RequestBody Banco atualizado) {
        return repository.findById(id)
                .map(banco -> {
                    banco.setNome(atualizado.getNome());
                    banco.setRua(atualizado.getRua());
                    banco.setBairro(atualizado.getBairro());
                    banco.setCep(atualizado.getCep());
                    banco.setNumero(atualizado.getNumero());
                    banco.setOpcional(atualizado.getOpcional());
                    banco.setEmail(atualizado.getEmail());
                    banco.setSenha(atualizado.getSenha());
                    banco.setCnpj(atualizado.getCnpj());
                    return new ResponseEntity<>(repository.save(banco), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar banco", description = "Remove um Banco do banco de dados")
    @ApiResponse(responseCode = "204", description = "banco deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "banco não encontrado")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
