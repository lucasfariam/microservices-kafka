package com.app.pedidos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    private LocalDateTime criadoEm = LocalDateTime.now();
}
