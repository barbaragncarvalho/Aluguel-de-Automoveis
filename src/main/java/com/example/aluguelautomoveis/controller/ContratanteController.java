package com.example.aluguelautomoveis.controller;

import com.example.aluguelautomoveis.model.Contratante;
import com.example.aluguelautomoveis.repository.ContratanteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contratantes")
@Tag(name = "Contratantes", description = "Operações CRUD para contratantes")
public class ContratanteController {

    @Autowired
    private ContratanteRepository repository;

    @PostMapping
    @Operation(summary = "Criar contratante", description = "Cadastra um novo contratante")
    @ApiResponse(responseCode = "201", description = "Contratante criado com sucesso")
    public ResponseEntity<Contratante> criar(@RequestBody Contratante contratante) {
        return new ResponseEntity<>(repository.save(contratante), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar contratantes", description = "Lista todos os contratantes")
    @ApiResponse(responseCode = "200", description = "Contratantes listados com sucesso")
    public ResponseEntity<List<Contratante>> listarTodos() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar contratante por ID", description = "Busca contratante pelo ID")
    @ApiResponse(responseCode = "200", description = "Contratante encontrado")
    @ApiResponse(responseCode = "404", description = "Contratante não encontrado")
    public ResponseEntity<Contratante> buscarPorId(@PathVariable Long id) {
        Optional<Contratante> contratante = repository.findById(id);
        return contratante.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar contratante", description = "Atualiza os dados de um contratante")
    @ApiResponse(responseCode = "200", description = "Contratante atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Contratante não encontrado")
    public ResponseEntity<Contratante> atualizar(@PathVariable Long id, @RequestBody Contratante atualizado) {
        return repository.findById(id)
                .map(contratante -> {
                    contratante.setRg(atualizado.getRg());
                    contratante.setCpf(atualizado.getCpf());
                    contratante.setProfissao(atualizado.getProfissao());
                    contratante.setRendimentos(atualizado.getRendimentos());
                    return new ResponseEntity<>(repository.save(contratante), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar contratante", description = "Remove um contratante do banco de dados")
    @ApiResponse(responseCode = "204", description = "Contratante deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Contratante não encontrado")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
