package com.TecNM_ITLP.API.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record DetalleCarritoDTO(
    @Schema(description = "ID del producto a agregar", example = "1")
    int productos_id,
    
    @Schema(description = "ID del usuario", example = "1")
    int usuarios_id,
    
    @Schema(description = "Cantidad deseada", example = "2")
    int cantidad
) {}