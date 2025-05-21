package com.app.pagamentos.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID pedidoId;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

    private LocalDateTime criadoEm = LocalDateTime.now();
}