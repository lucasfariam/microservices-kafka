package com.app.pagamentos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRecebidoDTO {
    private UUID id;
    private String descricao;
    private String status;
}