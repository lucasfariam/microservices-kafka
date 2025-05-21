package com.app.pedidos.listener;

import com.app.pedidos.dto.PagamentoRecebidoDTO;
import com.app.pedidos.entity.Pedido;
import com.app.pedidos.entity.StatusPedido;
import com.app.pedidos.repository.PedidoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PagamentoListener {

    @Autowired
    private PedidoRepository pedidoRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    private static final Map<String, StatusPedido> statusMap = new HashMap<>();

    static {
        statusMap.put("CONFIRMADO", StatusPedido.PAGO);
        statusMap.put("RECUSADO", StatusPedido.CANCELADO);
        statusMap.put("AGUARDANDO", StatusPedido.CRIADO);
    }

    @KafkaListener(topics = "pagamento-topic", groupId = "pedido-group")
    public void ouvirPagamento(String mensagemJson) {
        try {
            PagamentoRecebidoDTO pagamentoRecebido = mapper.readValue(mensagemJson, PagamentoRecebidoDTO.class);

            UUID pedidoId = pagamentoRecebido.getPedidoId();
            String statusPagamento = pagamentoRecebido.getStatus();

            Pedido pedido = pedidoRepository.findById(pedidoId).orElse(null);
            if (pedido != null) {
                StatusPedido novoStatus = statusMap.getOrDefault(statusPagamento.toUpperCase(), pedido.getStatus());
                pedido.setStatus(novoStatus);
                pedidoRepository.save(pedido);
                System.out.println("Status do pedido atualizado para: " + novoStatus);
            } else {
                System.out.println("Pedido não encontrado para ID: " + pedidoId);
            }
        } catch (JsonProcessingException e) {
            System.out.println("Erro ao desserializar mensagem JSON: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao processar mensagem: " + e.getMessage());
        }
    }

}
