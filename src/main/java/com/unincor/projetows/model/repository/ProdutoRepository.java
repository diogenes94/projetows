package com.unincor.projetows.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unincor.projetows.model.domain.Produto;
import java.util.List;


public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    boolean existsByDescricao(String descricao);
    Optional<Produto> findFirstByDescricao(String descricao);
}
