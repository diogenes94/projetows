package com.unincor.projetows.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unincor.projetows.controller.dto.EnderecoDTO;
import com.unincor.projetows.model.domain.Cliente;
import com.unincor.projetows.model.domain.Endereco;
import com.unincor.projetows.model.repository.ClienteRepository;

@RestController
@RequestMapping("/api/clientes")
public class ClienteApiController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/{clienteId}/endereco")
    public EnderecoDTO obterEndereco(@PathVariable Integer clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        Endereco e = (cliente != null) ? cliente.getEndereco() : null;
        return (e == null) ? null : EnderecoDTO.of(e);
    }

}
