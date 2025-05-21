package com.app.pedidos.controller;

import com.app.pedidos.dto.PedidoDTO;
import com.app.pedidos.entity.StatusPedido;
import com.app.pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public PedidoDTO criarPedido(@RequestParam String descricao) {
        return pedidoService.criarPedido(descricao);
    }

    @GetMapping
    public List<PedidoDTO> listarPedidos() {
        return pedidoService.listarTodos();
    }

    @PatchMapping("/{id}/status")
    public void atualizarStatusPedido(@PathVariable UUID id, @RequestParam StatusPedido status) {
        pedidoService.atualizarStatus(id, status);
    }
}
