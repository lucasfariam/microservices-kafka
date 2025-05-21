package com.app.pagamentos.producer;

import com.app.pagamentos.dto.PagamentoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaPagamentoProducer {

    private static final String TOPIC = "pagamento-topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void enviarMensagem(PagamentoDTO pagamentoDTO) {
        try {
            String mensagemJson = objectMapper.writeValueAsString(pagamentoDTO);
            kafkaTemplate.send(TOPIC, mensagemJson);
            System.out.println("Mensagem enviada para o Kafka: " + mensagemJson);
        } catch (JsonProcessingException e) {
            System.out.println("Erro ao converter pagamento para JSON: " + e.getMessage());
        }
    }
}
