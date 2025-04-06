package com.example.aluguelautomoveis.controller;

import com.example.aluguelautomoveis.model.Usuario;
import com.example.aluguelautomoveis.repository.UsuarioRepository;
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
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "Operações CRUD para usuários")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    @Operation(summary = "Criar usuário", description = "Cadastra um novo usuário")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(repository.save(usuario), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar usuários", description = "Lista todos os usuários")
    @ApiResponse(responseCode = "200", description = "Usuários listados com sucesso")
    public ResponseEntity<List<Usuario>> listarTodos() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Busca usuário pelo ID")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = repository.findById(id);
        return usuario.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario atualizado) {
        return repository.findById(id)
                .map(usuario -> {
                    usuario.setRua(atualizado.getRua());
                    usuario.setBairro(atualizado.getBairro());
                    usuario.setCep(atualizado.getCep());
                    usuario.setNumero(atualizado.getNumero());
                    usuario.setOpcional(atualizado.getOpcional());
                    usuario.setEmail(atualizado.getEmail());
                    usuario.setSenha(atualizado.getSenha());
                    return new ResponseEntity<>(repository.save(usuario), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário", description = "Remove um usuário do banco de dados")
    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
