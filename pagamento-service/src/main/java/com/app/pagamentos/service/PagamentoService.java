package com.app.pagamentos.service;

import com.app.pagamentos.dto.PagamentoDTO;
import com.app.pagamentos.dto.PedidoRecebidoDTO;
import com.app.pagamentos.entity.Pagamento;
import com.app.pagamentos.entity.StatusPagamento;
import com.app.pagamentos.mapper.PagamentoMapper;
import com.app.pagamentos.repository.PagamentoRepository;
import com.app.pagamentos.producer.KafkaPagamentoProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private KafkaPagamentoProducer kafkaPagamentoProducer;

    @Autowired
    private PagamentoMapper pagamentoMapper;

    private static final Map<String, StatusPagamento> statusMap = new HashMap<>();

    static {
        statusMap.put("CRIADO", StatusPagamento.AGUARDANDO);
        statusMap.put("CANCELADO", StatusPagamento.RECUSADO);
    }

    public PagamentoDTO processarPagamento(PedidoRecebidoDTO pedidoDTO) {
        Pagamento pagamento = new Pagamento();
        pagamento.setPedidoId(pedidoDTO.getId());
        pagamento.setStatus(statusMap.getOrDefault(pedidoDTO.getStatus(), StatusPagamento.AGUARDANDO));

        pagamentoRepository.save(pagamento);
        return pagamentoMapper.toDTO(pagamento);
    }

    public List<PagamentoDTO> listarTodos() {
        return pagamentoRepository.findAll()
                .stream()
                .map(pagamentoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PagamentoDTO atualizarStatus(UUID id, String novoStatus) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado: " + id));

        StatusPagamento status = StatusPagamento.valueOf(novoStatus.toUpperCase());
        pagamento.setStatus(status);

        Pagamento pagamentoAtualizado = pagamentoRepository.save(pagamento);

        PagamentoDTO pagamentoDTO = pagamentoMapper.toDTO(pagamentoAtualizado);
        kafkaPagamentoProducer.enviarMensagem(pagamentoDTO);

        return pagamentoDTO;
    }


}