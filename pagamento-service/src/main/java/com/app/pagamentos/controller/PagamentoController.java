package com.app.pagamentos.controller;

import com.app.pagamentos.dto.PagamentoDTO;
import com.app.pagamentos.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public List<PagamentoDTO> listarTodos() {
        return pagamentoService.listarTodos();
    }

    @PatchMapping("/{id}/status")
    public PagamentoDTO atualizarStatus(
            @PathVariable UUID id,
            @RequestParam String status
    ) {
        return pagamentoService.atualizarStatus(id, status);
    }

}