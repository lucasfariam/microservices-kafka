package com.app.pagamentos.dto;

import com.app.pagamentos.entity.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoDTO {
    private UUID idPagamento;
    private UUID pedidoId;
    private StatusPagamento statusPagamento;
}