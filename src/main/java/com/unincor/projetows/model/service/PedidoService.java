package com.unincor.projetows.model.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unincor.projetows.exceptions.PedidoException;
import com.unincor.projetows.model.domain.Pedido;
import com.unincor.projetows.model.domain.ProdutoPedido;
import com.unincor.projetows.model.repository.PedidoRepository;
import com.unincor.projetows.model.repository.ProdutoPedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;    
    @Autowired
    private ProdutoPedidoRepository produtoPedidoRepository;

    public Pedido salvarNovoPedido(Pedido pedido) {
        if(pedido.getId() != null) {
            throw new PedidoException("Este pedido já se encontra salvo!");
        }
        if(pedido.getProdutosPedidos().isEmpty()) {
            throw new PedidoException("Não existem produtos informados!");
        }
        pedido.setDataPedido(LocalDateTime.now());
        // Faz um for na lista de produtos e calcula os preços totalizados
        pedido.getProdutosPedidos().forEach(ProdutoPedido::calcularPreco);
        // totaliza os valores com base nos totalizadores de cada item
        pedido.totalizarPedido();
        //Salva um pedido e retorna um objeto com ID
        var pedidoSalvo = pedidoRepository.save(pedido);
        //Percorre todos os objetos de ProdutoPedido, e o vincula com o pedidosalvo que contém ID
        pedido.getProdutosPedidos().forEach(prodPed -> prodPed.setPedido(pedidoSalvo));
        //Depois de preenchido, salvar todos os registros no banco de dados.
        produtoPedidoRepository.saveAll(pedido.getProdutosPedidos());
        return pedidoSalvo;

    }



}
