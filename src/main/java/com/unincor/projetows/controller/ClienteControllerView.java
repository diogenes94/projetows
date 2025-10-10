package com.unincor.projetows.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unincor.projetows.model.domain.Cliente;
import com.unincor.projetows.model.domain.Endereco;
import com.unincor.projetows.model.repository.ClienteRepository;
import com.unincor.projetows.model.service.ClienteService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/clientes-site")
public class ClienteControllerView {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "clientes-site/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        Cliente cliente = new Cliente();
        cliente.setEndereco(new Endereco());
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Novo Cliente");
        return "clientes-site/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult br, RedirectAttributes ra, Model model) {

        if(br.hasErrors()) {
            model.addAttribute("titulo", (cliente.getId() == null) ? "Novo Cliente" : "Editar Cliente");
            return "clientes-site/form";
        }
        clienteService.salvar(cliente);
        ra.addFlashAttribute("ok", "Cliente salvo com sucesso!");
        return "redirect:/clientes-site";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model) {
        var cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));
        if(cliente.getEndereco() == null) {
            cliente.setEndereco(new Endereco());
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Editar Cliente");
        return "clientes-site/form";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Integer id, RedirectAttributes ra) {
        clienteRepository.deleteById(id);
        ra.addFlashAttribute("ok","Cliente excluído");
        return "redirect:/clientes-site";
    }

}
