package com.example.aluguelautomoveis.controller;

import com.example.aluguelautomoveis.model.Automovel;
import com.example.aluguelautomoveis.repository.AutomovelRepository;
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
@RequestMapping("/api/automoveis")
@Tag(name = "Automóveis", description = "Operações CRUD para automóveis")
public class AutomovelController {
    @Autowired
    private AutomovelRepository repository;

    @PostMapping
    @Operation(summary = "Criar automóvel", description = "Cadastra um novo automóvel")
    @ApiResponse(responseCode = "201", description = "Automóvel criado com sucesso")
    public ResponseEntity<Automovel> criar(@Valid @RequestBody Automovel automovel) {
        return new ResponseEntity<>(repository.save(automovel), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar automóvel", description = "Lista todos os automóveis")
    @ApiResponse(responseCode = "200", description = "Automóveis listados com sucesso")
    public ResponseEntity<List<Automovel>> listarTodos() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar automóvel por ID", description = "Busca automóvel pelo ID")
    @ApiResponse(responseCode = "200", description = "Automóvel encontrado")
    @ApiResponse(responseCode = "404", description = "Automóvel não encontrado")
    public ResponseEntity<Automovel> buscarPorId(@PathVariable Long id) {
        Optional<Automovel> automovel = repository.findById(id);
        return automovel.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar automóvel", description = "Atualiza os dados de um automóvel")
    @ApiResponse(responseCode = "200", description = "Automóvel atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Automóvel não encontrado")
    public ResponseEntity<Automovel> atualizar(@Valid @PathVariable Long id, @RequestBody Automovel atualizado) {
        return repository.findById(id)
                .map(automovel -> {
                    automovel.setMatricula(atualizado.getMatricula());
                    automovel.setAno(atualizado.getAno());
                    automovel.setMarca(atualizado.getMarca());
                    automovel.setModelo(atualizado.getModelo());
                    automovel.setPlaca(atualizado.getPlaca());
                    automovel.setDisponivel(atualizado.isDisponivel());
                    automovel.setProprietario(atualizado.getProprietario());

                    return new ResponseEntity<>(repository.save(automovel), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar automóvel", description = "Remove um automóvel do banco de dados")
    @ApiResponse(responseCode = "204", description = "Automóvel deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Automóvel não encontrado")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
