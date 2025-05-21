package com.app.pagamentos.mapper;

import com.app.pagamentos.dto.PagamentoDTO;
import com.app.pagamentos.entity.Pagamento;
import org.springframework.stereotype.Component;

@Component
public class PagamentoMapper {

    public PagamentoDTO toDTO(Pagamento pagamento) {
        return new PagamentoDTO(pagamento.getId(), pagamento.getPedidoId(), pagamento.getStatus());
    }

}
