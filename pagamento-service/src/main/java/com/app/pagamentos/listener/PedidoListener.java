package com.app.pagamentos.listener;

import com.app.pagamentos.dto.PedidoRecebidoDTO;
import com.app.pagamentos.dto.PagamentoDTO;
import com.app.pagamentos.service.PagamentoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class PedidoListener {

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "pedido-topic", groupId = "pagamento-group")
    public void ouvirPedidos(String mensagemJson) throws JsonProcessingException {
        try {
            PedidoRecebidoDTO pedidoRecebido = mapper.readValue(mensagemJson, PedidoRecebidoDTO.class);
            PagamentoDTO pagamento = pagamentoService.processarPagamento(pedidoRecebido);

            String retorno = mapper.writeValueAsString(pagamento);
            kafkaTemplate.send("pagamento-topic", retorno);
        } catch (Exception e) {
            System.out.println("Erro ao processar mensagem: " + e.getMessage());
        }
    }
}