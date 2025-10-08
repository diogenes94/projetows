package com.unincor.projetows.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unincor.projetows.model.domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
