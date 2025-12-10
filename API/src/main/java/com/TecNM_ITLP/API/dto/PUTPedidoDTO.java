package com.TecNM_ITLP.API.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PUTPedidoDTO(
    @Schema(description = "Actualizar importe envío (si hubo error)", example = "200.00")
    Double importe_envio,
    
    @Schema(description = "Actualizar importe productos", example = "5200.00")
    Double importe_productos
) {}