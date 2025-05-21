package com.app.pedidos.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagamentoRecebidoDTO {

    @JsonProperty("idPagamento")
    private UUID id;

    private UUID pedidoId;

    @JsonProperty("statusPagamento")
    private String status;
}
