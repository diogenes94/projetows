package com.unincor.projetows.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping
    public String home(Model model) {
        model.addAttribute("titulo", "Início");
        model.addAttribute("active", "home");
        return "home/index";
    }
    

}
