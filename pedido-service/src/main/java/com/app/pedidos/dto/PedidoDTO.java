package com.app.pedidos.dto;

import com.app.pedidos.entity.StatusPedido;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private UUID id;
    private String descricao;
    private StatusPedido status;
}
