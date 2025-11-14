package com.unincor.projetows.controller;

import java.util.List;

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

import com.unincor.projetows.exceptions.PedidoException;
import com.unincor.projetows.model.domain.Cliente;
import com.unincor.projetows.model.domain.Pedido;
import com.unincor.projetows.model.domain.Produto;
import com.unincor.projetows.model.repository.ClienteRepository;
import com.unincor.projetows.model.repository.PedidoRepository;
import com.unincor.projetows.model.repository.ProdutoRepository;
import com.unincor.projetows.model.service.PedidoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pedidos-site")
public class PedidoControllerView {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private PedidoService pedidoService;

    // LISTA
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pedidos", pedidoRepository.findAll());
        model.addAttribute("titulo", "Pedidos");
        return "pedidos-site/lista";
    }

    // NOVO (FORM)
    @GetMapping("/novo")
    public String novo(Model model) {
        Pedido pedido = new Pedido();
        // Garanta pelo menos uma linha de item vazia se desejar:
        // pedido.getProdutosPedidos().add(new ProdutoPedido());

        carregarApoios(model, "Novo Pedido");
        model.addAttribute("pedido", pedido);
        return "pedidos-site/form";
    }

    // NOVO (POST) >>> URL: /pedidos-site/novo
    @PostMapping("/novo")
    public String salvarNovo(@Valid @ModelAttribute("pedido") Pedido pedido,
            BindingResult br,
            RedirectAttributes ra,
            Model model) {
        if (br.hasErrors()) {
            carregarApoios(model, "Novo Pedido");
            return "pedidos-site/form";
        }
        try {
            pedidoService.salvarNovoPedido(pedido); // <<< usa o service
            ra.addFlashAttribute("ok", "Pedido salvo com sucesso!");
            return "redirect:/pedidos-site";
        } catch (PedidoException e) {
            model.addAttribute("erro", e.getMessage());
            carregarApoios(model, "Novo Pedido");
            return "pedidos-site/form";
        }
    }

    // EDITAR (FORM) >>> URL: /pedidos-site/{id}/editar (GET)
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + id));
        carregarApoios(model, "Editar Pedido");
        model.addAttribute("pedido", pedido);
        return "pedidos-site/form";
    }

    // EDITAR (POST) >>> URL: /pedidos-site/{id}/editar (POST)
    @PostMapping("/{id}/editar")
    public String salvarEdicao(@PathVariable Integer id,
            @Valid @ModelAttribute("pedido") Pedido pedido,
            BindingResult br,
            RedirectAttributes ra,
            Model model) {
        if (br.hasErrors()) {
            carregarApoios(model, "Editar Pedido");
            return "pedidos-site/form";
        }
        try {
            pedido.setId(id); // garante o ID
            pedidoService.salvarEdicao(pedido); // <<< usa o service
            ra.addFlashAttribute("ok", "Pedido atualizado com sucesso!");
            return "redirect:/pedidos-site";
        } catch (PedidoException e) {
            model.addAttribute("erro", e.getMessage());
            carregarApoios(model, "Editar Pedido");
            return "pedidos-site/form";
        }
    }

    // EXCLUIR
    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Integer id, RedirectAttributes ra) {
        pedidoRepository.deleteById(id);
        ra.addFlashAttribute("ok", "Pedido excluído.");
        return "redirect:/pedidos-site";
    }

    // ==== APOIOS ====
    private void carregarApoios(Model model, String titulo) {
        List<Cliente> clientes = clienteRepository.findAll();
        List<Produto> produtos = produtoRepository.findAll();
        model.addAttribute("clientes", clientes);
        model.addAttribute("produtos", produtos);
        model.addAttribute("titulo", titulo);
    }
}
