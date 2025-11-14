package com.unincor.projetows.model.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unincor.projetows.exceptions.PedidoException;
import com.unincor.projetows.model.domain.Pedido;
import com.unincor.projetows.model.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido salvarNovoPedido(Pedido pedido) {
        if(pedido.getId() != null) {
            throw new PedidoException("Este pedido já se encontra salvo!");
        }
        if(pedido.getCliente() == null || pedido.getCliente().getId() ==null) {
            throw new PedidoException("Cliente não informado!");
        }
        if(pedido.getProdutosPedidos().isEmpty()) {
            throw new PedidoException("Não existem produtos informados!");
        }
        pedido.setDataPedido(LocalDateTime.now());
        if(pedido.getValorFrete() == null) {
            pedido.setValorFrete(0.);
        }
        // Faz um for na lista de produtos e calcula os preços totalizados
        pedido.setItensFromForm(pedido.getProdutosPedidos());

        if(pedido.getProdutosPedidos().isEmpty()) {
            throw new PedidoException("Nenhum item válido informado.");
        }

        //Salva um pedido e retorna um objeto com ID
        return pedidoRepository.save(pedido);

    }

    public Pedido salvarEdicao(Pedido pedido) {
        if(pedido.getId() == null) {
            throw new PedidoException("ID do pedido não informado para edição.");
        }
        Pedido existente = pedidoRepository.findById(pedido.getId())
            .orElseThrow(() -> new PedidoException("Pedido não encontrado: " + pedido.getId()));
        
        existente.setCliente(pedido.getCliente());
        existente.setEndereco(pedido.getEndereco());
        existente.setObservacaoDoCliente(pedido.getObservacaoDoCliente());
        existente.setValorFrete(pedido.getValorFrete() == null ? 0. : pedido.getValorFrete());
        /* recria a lista de itens no pedido existente */
        existente.setItensFromForm(pedido.getProdutosPedidos());

        if(existente.getProdutosPedidos().isEmpty()) {
            throw new PedidoException("Nenhum item válido informado!");
        }

        return pedidoRepository.save(existente);
    }


}
