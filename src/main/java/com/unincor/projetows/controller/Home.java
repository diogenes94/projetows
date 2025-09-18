package com.unincor.projetows.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @GetMapping
    public String ola() {
        return "Hello World";
    }

    @GetMapping("/oi")
    public String oi() {
        return "Olá! Estou aqui";
    }

    @GetMapping("/oi/denovo")
    public String oiDenovo() {
        return "Olá! Estou em outro lugar";
    }

    @PostMapping
    public String post() {
        return "Chamou um post";
    }

}
