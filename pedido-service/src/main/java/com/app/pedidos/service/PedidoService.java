package com.app.pedidos.service;

import com.app.pedidos.dto.PedidoDTO;
import com.app.pedidos.entity.Pedido;
import com.app.pedidos.entity.StatusPedido;
import com.app.pedidos.mapper.PedidoMapper;
import com.app.pedidos.producer.KafkaPedidoProducer;
import com.app.pedidos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private KafkaPedidoProducer kafkaPedidoProducer;

    @Autowired
    private PedidoMapper pedidoMapper;

    public PedidoDTO criarPedido(String descricao) {
        Pedido pedido = new Pedido();
        pedido.setDescricao(descricao);
        pedido.setStatus(StatusPedido.CRIADO);

        pedidoRepository.save(pedido);
        kafkaPedidoProducer.enviarMensagem(pedidoMapper.toDTO(pedido));
        return pedidoMapper.toDTO(pedido);
    }

    public List<PedidoDTO> listarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void atualizarStatus(UUID id, StatusPedido status) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id));
        pedido.setStatus(status);
        pedidoRepository.save(pedido);
    }
}
