package com.TecNM_ITLP.API.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PUTDetallesPedidoDTO(
    @Schema(description = "Nueva cantidad", example = "3")
    int cantidad,
    
    @Schema(description = "Nuevo precio (si aplica corrección)", example = "5000.00")
    Double precio
) {}