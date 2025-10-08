package com.unincor.projetows.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unincor.projetows.model.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
