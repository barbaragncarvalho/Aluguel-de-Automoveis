package com.example.aluguelautomoveis.controller;

import com.example.aluguelautomoveis.model.Contrato;
import com.example.aluguelautomoveis.repository.ContratoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contratos")
@Tag(name = "Contratos", description = "Operações CRUD para contratos")
public class ContratoController {

    @Autowired
    private ContratoRepository repository;

    @PostMapping
    @Operation(summary = "Criar contrato", description = "Cadastra um novo contrato")
    @ApiResponse(responseCode = "201", description = "Contrato criado com sucesso")
    public ResponseEntity<Contrato> criar(@Valid @RequestBody Contrato contrato) {
        return new ResponseEntity<>(repository.save(contrato), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar contratos", description = "Lista todos os contratos")
    @ApiResponse(responseCode = "200", description = "Contratos listados com sucesso")
    public ResponseEntity<List<Contrato>> listarTodos() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar contrato por ID", description = "Busca contrato pelo ID")
    @ApiResponse(responseCode = "200", description = "Contrato encontrado")
    @ApiResponse(responseCode = "404", description = "Contrato não encontrado")
    public ResponseEntity<Contrato> buscarPorId(@PathVariable Long id) {
        Optional<Contrato> contrato = repository.findById(id);
        return contrato.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar contrato", description = "Atualiza os dados de um contrato")
    @ApiResponse(responseCode = "200", description = "Contrato atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Contrato não encontrado")
    public ResponseEntity<Contrato> atualizar(@Valid @PathVariable Long id, @RequestBody Contrato atualizado) {
        return repository.findById(id)
                .map(contrato -> {
                    contrato.setDataInicio(atualizado.getDataInicio());
                    contrato.setDataFim(atualizado.getDataFim());
                    contrato.setTipo(atualizado.getTipo());
                    return new ResponseEntity<>(repository.save(contrato), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar contrato", description = "Remove um contrato do banco de dados")
    @ApiResponse(responseCode = "204", description = "Contrato deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Contrato não encontrado")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
