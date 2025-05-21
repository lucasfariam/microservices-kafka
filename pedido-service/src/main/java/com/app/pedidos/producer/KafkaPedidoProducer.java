package com.app.pedidos.producer;

import com.app.pedidos.dto.PedidoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaPedidoProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic = "pedido-topic";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaPedidoProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarMensagem(PedidoDTO pedidoRecebido) {
        try {
            String mensagemJson = objectMapper.writeValueAsString(pedidoRecebido);
            kafkaTemplate.send(topic, mensagemJson);
            System.out.println("Mensagem enviada para o Kafka: " + pedidoRecebido);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro", e);
        }
    }
}
