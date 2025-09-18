package com.unincor.projetows.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.unincor.projetows.model.domain.Cliente;
import com.unincor.projetows.model.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    //List<Cliente> clientes = new ArrayList<>();

    @GetMapping
    public String inicio() {
        return "Bem vindo ao controller de cliente";
    }

    @GetMapping("/listar")
    public List<Cliente> listar() {        
        return clienteRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvarCliente(@RequestBody Cliente cliente) {
        var clienteSalvo = clienteRepository.save(cliente);
        return clienteSalvo;
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Integer clienteId) {
        var cliente = clienteRepository.findById(clienteId);
        if(cliente.isPresent()) {
            clienteRepository.delete(cliente.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Fazer um método que busca um cliente pelo ID, e se não encontrar retornar null
    @GetMapping("/{clienteId}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Integer clienteId) {
        var cliente = clienteRepository.findById(clienteId);
        if(cliente.isPresent()) {
           return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();
    }
}
