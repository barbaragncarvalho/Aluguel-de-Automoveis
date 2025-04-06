package com.example.aluguelautomoveis.controller;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.aluguelautomoveis.model.Empresa;
import com.example.aluguelautomoveis.repository.EmpresaRepository;

@RestController
@RequestMapping("/api/empresas")
@Tag(name = "Empresas", description = "Operações CRUD para empresas")
public class EmpresaController {
     @Autowired
    private EmpresaRepository repository;

    @PostMapping
    @Operation(summary = "Criar empresa", description = "Cadastra uma nova empresa")
    @ApiResponse(responseCode = "201", description = "empresa criada com sucesso")
    public ResponseEntity<Empresa> criar(@RequestBody Empresa empresa) {
        return new ResponseEntity<>(repository.save(empresa), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar empresas", description = "Lista todas as empresa")
    @ApiResponse(responseCode = "200", description = "empresas listadas com sucesso")
    public ResponseEntity<List<Empresa>> listarTodos() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar empresa por ID", description = "Busca empresa pelo ID")
    @ApiResponse(responseCode = "200", description = "empresa encontrada")
    @ApiResponse(responseCode = "404", description = "empresa não encontrada")
    public ResponseEntity<Empresa> buscarPorId(@PathVariable Long id) {
        Optional<Empresa> empresa = repository.findById(id);
        return empresa.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar empresa", description = "Atualiza os dados de uma empresa")
    @ApiResponse(responseCode = "200", description = "empresa atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "empresa não encontrada")
    public ResponseEntity<Empresa> atualizar(@PathVariable Long id, @RequestBody Empresa atualizado) {
        return repository.findById(id)
                .map(empresa -> {
                    empresa.setNome(atualizado.getNome());
                    empresa.setRua(atualizado.getRua());
                    empresa.setBairro(atualizado.getBairro());
                    empresa.setCep(atualizado.getCep());
                    empresa.setNumero(atualizado.getNumero());
                    empresa.setOpcional(atualizado.getOpcional());
                    empresa.setEmail(atualizado.getEmail());
                    empresa.setSenha(atualizado.getSenha());
                    empresa.setCnpj(atualizado.getCnpj());
                    return new ResponseEntity<>(repository.save(empresa), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar empresa", description = "Remove uma empresa do banco de dados")
    @ApiResponse(responseCode = "204", description = "empresa deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "empresa não encontrada")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
