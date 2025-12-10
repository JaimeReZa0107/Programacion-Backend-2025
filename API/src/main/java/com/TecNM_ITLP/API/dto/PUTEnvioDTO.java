package com.TecNM_ITLP.API.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PUTEnvioDTO(
    @Schema(description = "Actualizar fecha entrega", example = "2025-12-16")
    String fecha_entrega,
    
    @Schema(description = "Nuevo estado", example = "ENTREGADO")
    String estado
) {}