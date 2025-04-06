package com.example.aluguelautomoveis.controller;

import com.example.aluguelautomoveis.model.Cliente;
import com.example.aluguelautomoveis.repository.ClienteRepository;
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
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Operações CRUD para gerenciamento de clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    @Operation(summary = "Criar cliente", description = "Cadastra um novo cliente")
    @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso")
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        Cliente novoCliente = clienteRepository.save(cliente);
        return new ResponseEntity<>(novoCliente, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar clientes", description = "Lista todos os clientes")
    @ApiResponse(responseCode = "200", description = "Clientes listados com sucesso")
    public ResponseEntity<List<Cliente>> listarClientes() {
        return new ResponseEntity<>(clienteRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/count")
    @Operation(
        summary = "Contar clientes", 
        description = "Retorna o total de clientes cadastrados"
    )
    @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso")
    public ResponseEntity<Long> contarClientes() {
        return ResponseEntity.ok(clienteRepository.count());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Busca cliente pelo ID")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente")
    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNome(clienteAtualizado.getNome());
                    cliente.setCpf(clienteAtualizado.getCpf());
                    return new ResponseEntity<>(clienteRepository.save(cliente), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cliente", description = "Remove um cliente do banco de dados")
    @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}