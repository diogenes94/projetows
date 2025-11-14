package com.unincor.projetows.model.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "Pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime dataPedido;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @NotNull
    private Double valorTotal = 0.;
    @NotNull
    private Double valorDesconto = 0.0;
    @NotNull
    private Double valorProdutos = 0.;
    private Double valorFrete = .0;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
    private String observacaoDoCliente;
    @NotNull
    private Boolean cancelado = false;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoPedido> produtosPedidos = new ArrayList<>();

    public void totalizarPedido() {
        this.valorProdutos = produtosPedidos.stream()
                .mapToDouble(prod -> prod.getValorProduto() == null ? 0. : prod.getValorProduto()).sum();
        // totalizar valor desconto e valor produtos
        this.valorDesconto = produtosPedidos.stream()
                .mapToDouble(prod -> prod.getValorDesconto() == null ? 0. : prod.getValorDesconto()).sum();
        // valor total é o valor produtos menos o desconto.
        // this.valorTotal = valorProdutos - valorDesconto;
        this.valorTotal = produtosPedidos.stream()
                .mapToDouble(prod -> prod.getValorTotal() == null ? 0. : prod.getValorTotal()).sum()
                + (this.valorFrete == null ? 0. : this.valorFrete);

    }

    // Pedido.java
    public void setItensFromForm(List<ProdutoPedido> itensForm) {
        // 1) Define a origem sem risco de esvaziar a lista que estamos iterando
        final List<ProdutoPedido> origem;
        if (itensForm == null) {
            origem = java.util.Collections.emptyList();
        } else if (itensForm == this.produtosPedidos) {
            // se veio a PRÓPRIA lista do pedido (bind do form), iteramos numa cópia
            origem = new java.util.ArrayList<>(itensForm);
        } else {
            origem = itensForm;
        }

        final List<ProdutoPedido> nova = new java.util.ArrayList<>();
        for (ProdutoPedido it : origem) {
            if (it == null)
                continue;
            if (it.getProduto() == null || it.getProduto().getId() == null)
                continue;

            // Se quantidade for BigDecimal, troque por compareTo(BigDecimal.ZERO) > 0
            if (it.getQuantidade() == null || it.getQuantidade() <= 0)
                continue;

            it.setPedido(this); // garante o back-reference
            it.calcularPreco(); // garante valores unitários/total do item
            nova.add(it);
        }

        // 3) Substitui o conteúdo da lista do pedido de uma vez, sem quebrar o bind
        this.produtosPedidos.clear();
        this.produtosPedidos.addAll(nova);

        // 4) Recalcula os totais do pedido
        this.totalizarPedido();
    }

}
