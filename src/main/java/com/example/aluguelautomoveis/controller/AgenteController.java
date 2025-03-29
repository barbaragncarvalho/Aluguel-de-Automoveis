// AgenteController.java
package com.example.aluguelautomoveis.controller;

import com.example.aluguelautomoveis.model.Agente;
import com.example.aluguelautomoveis.repository.AgenteRepository;
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
@RequestMapping("/api/agentes")
@Tag(name = "Agentes", description = "Gerenciamento de agentes")
public class AgenteController {

    @Autowired
    private AgenteRepository repository;

    @PostMapping
    @Operation(summary = "Criar novo agente")
    @ApiResponse(responseCode = "201", description = "Agente criado")
    public ResponseEntity<Agente> criar(@RequestBody Agente agente) {
        return new ResponseEntity<>(repository.save(agente), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todos agentes")
    public ResponseEntity<List<Agente>> listarTodos() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar agente por ID")
    @ApiResponse(responseCode = "404", description = "Agente não encontrado")
    public ResponseEntity<Agente> buscarPorId(@PathVariable Long id) {
        Optional<Agente> agente = repository.findById(id);
        return agente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar agente")
    public ResponseEntity<Agente> atualizar(@PathVariable Long id, @RequestBody Agente atualizado) {
        return repository.findById(id)
                .map(agente -> {
                    agente.setNome(atualizado.getNome());
                    agente.setRua(atualizado.getRua());
                    agente.setBairro(atualizado.getBairro());
                    agente.setCep(atualizado.getCep());
                    agente.setNumero(atualizado.getNumero());
                    agente.setEmail(atualizado.getEmail());
                    agente.setSenha(atualizado.getSenha());
                    agente.setCnpj(atualizado.getCnpj());
                    return new ResponseEntity<>(repository.save(agente), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar agente")
    @ApiResponse(responseCode = "204", description = "Agente removido")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
