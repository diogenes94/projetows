package com.unincor.projetows.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unincor.projetows.model.domain.Cliente;
import java.time.LocalDate;


public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    
    List<Cliente> findByNome(String nome);
    List<Cliente> findByNomeOrderByNome(String nome);
    List<Cliente> findByDataNascimentoBetween(LocalDate inicio, LocalDate fim);
}
