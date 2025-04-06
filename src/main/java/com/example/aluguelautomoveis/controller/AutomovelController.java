package com.example.aluguelautomoveis.controller;

import com.example.aluguelautomoveis.model.Automovel;
import com.example.aluguelautomoveis.model.Pessoa;
import com.example.aluguelautomoveis.repository.AutomovelRepository;
import com.example.aluguelautomoveis.repository.BancoRepository;
import com.example.aluguelautomoveis.repository.ClienteRepository;
import com.example.aluguelautomoveis.repository.EmpresaRepository;

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
    @Autowired
    private BancoRepository bancoRepository;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    @Operation(summary = "Criar automóvel", description = "Cadastra um novo automóvel")
    @ApiResponse(responseCode = "201", description = "Automóvel criado com sucesso")
    public ResponseEntity<Automovel> criar(@Valid @RequestBody Automovel automovel) {
        Pessoa proprietario = null;
        String tipo = automovel.getProprietario().getTipo();
        Long id = automovel.getProprietario().getId();

        switch (tipo) {
            case "banco":
                proprietario = bancoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Banco não encontrado"));
                break;
            case "empresa":
                proprietario = empresaRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
                break;
            case "cliente":
                proprietario = clienteRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
                break;
            default:
                throw new RuntimeException("Tipo de proprietário inválido");
        }

        automovel.setProprietario(proprietario);
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
