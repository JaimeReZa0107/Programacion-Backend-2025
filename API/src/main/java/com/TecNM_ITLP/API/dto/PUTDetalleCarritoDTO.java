package com.TecNM_ITLP.API.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PUTDetalleCarritoDTO(
    @Schema(description = "Nueva cantidad", example = "5")
    int cantidad,
    
    @Schema(description = "ID del producto (para validar)", example = "1")
    int productos_id,
    
    @Schema(description = "ID del usuario (para validar)", example = "1")
    int usuarios_id
) {}