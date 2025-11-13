package com.unincor.projetows.controller.dto;

import com.unincor.projetows.model.domain.Endereco;

public record EnderecoDTO(
        Integer id, String logradouro, String numero,
        String complemento, String bairro, String cidade, String uf, String cep) {
    public static EnderecoDTO of(Endereco e) {
        return new EnderecoDTO(e.getId(), e.getLogradouro(), e.getNumero(), 
            e.getComplemento(), e.getBairro(), e.getCidade(), e.getUf(), e.getCep());
    }
}
