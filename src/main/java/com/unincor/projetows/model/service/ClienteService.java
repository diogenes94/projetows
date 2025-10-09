package com.unincor.projetows.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unincor.projetows.exceptions.ClienteException;
import com.unincor.projetows.model.domain.Cliente;
import com.unincor.projetows.model.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) {
        if(cliente == null) {
            throw new ClienteException("O cliente informado não é válido!");
        }
        var clienteBusca = clienteRepository.findFirstByCpf(cliente.getCpf());
        if((cliente.getId() == null && clienteBusca.isPresent())
                || (cliente.getId() != null && !cliente.equals(clienteBusca.get()))) {
            throw new ClienteException("O cliente com o CPF " + cliente.getCpf() + " já está cadastrado");
        }

        return clienteRepository.save(cliente);
    }



}
