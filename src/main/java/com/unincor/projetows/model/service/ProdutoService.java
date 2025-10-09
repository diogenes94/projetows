package com.unincor.projetows.model.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unincor.projetows.exceptions.ProdutoException;
import com.unincor.projetows.model.domain.Produto;
import com.unincor.projetows.model.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    public Produto salvar(Produto produto) {
        if (produto == null) {
            throw new ProdutoException("Nenhum produto válido informado");
        }

        Optional<Produto> produtoBusca = produtoRepository.findFirstByDescricao(produto.getDescricao());
        if ((produto.getId() == null && produtoBusca.isPresent())
                || (produto.getId() != null && !produto.equals(produtoBusca.get()))) {
            throw new ProdutoException("O produto com a descrição '" + produto.getDescricao() + "' já existe!");
        }

        return produtoRepository.save(produto);
    }

}
