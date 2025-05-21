package com.app.pedidos.mapper;

import com.app.pedidos.dto.PedidoDTO;
import com.app.pedidos.entity.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public PedidoDTO toDTO(Pedido pedido) {
        return new PedidoDTO(pedido.getId(), pedido.getDescricao(), pedido.getStatus());
    }

}