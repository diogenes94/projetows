package com.unincor.projetows.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unincor.projetows.exceptions.ClienteException;
import com.unincor.projetows.model.domain.Cliente;
import com.unincor.projetows.model.repository.ClienteRepository;

@Controller
@RequestMapping("/clientes-site")
public class ClienteControllerView {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "clientes-site/lista";
    }

}
