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

import com.unincor.projetows.exceptions.ProdutoException;
import com.unincor.projetows.model.domain.Produto;
import com.unincor.projetows.model.repository.ProdutoRepository;
import com.unincor.projetows.model.service.ProdutoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/produtos-site")
public class ProdutoControllerView {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", produtoRepository.findAll());
        return "produtos-site/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("titulo", "Novo Produto");
        return "produtos-site/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("produto") Produto produto,
            BindingResult br, RedirectAttributes ra, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("titulo",
                    (produto.getId() == null
                            ? "Novo Produto"
                            : "Editar Produto"));
            return "produtos-site/form";
        }

        try {
            produtoService.salvar(produto);
            ra.addFlashAttribute("ok", "Produto salvo com sucesso!");
            return "redirect:/produtos-site";
        } catch (ProdutoException ex) {
            br.rejectValue("descricao", "descricao.duplicada",
                    ex.getMessage());
            model.addAttribute("titulo",
                    (produto.getId() == null
                            ? "Novo Produto"
                            : "Editar Produto"));
            return "produtos-site/form";
        }

    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model) {
        Produto produto = produtoRepository.findById(id).orElse(null);
        model.addAttribute("produto", produto);
        model.addAttribute("titulo", "Editar Produto");
        return "produtos-site/form";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Integer id, RedirectAttributes ra) {
        produtoRepository.deleteById(id);
        ra.addFlashAttribute("ok", "Produto Excluído.");
        return "redirect:/produtos-site";
    }

}
