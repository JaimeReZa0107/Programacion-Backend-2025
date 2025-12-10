package com.TecNM_ITLP.API.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PUTPedidoDTO(
    @Schema(description = "Fecha y hora de pago (YYYY-MM-DD HH:MM:SS)", example = "2023-12-01 14:30:00")
    String fecha_hora_pago,
    
    @Schema(description = "Método de pago utilizado", example = "1")
    int metodos_pago_id
) {}