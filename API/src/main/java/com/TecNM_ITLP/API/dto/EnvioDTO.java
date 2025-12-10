package com.TecNM_ITLP.API.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record EnvioDTO(
    @Schema(description = "Fecha de salida (YYYY-MM-DD)", example = "2025-12-11")
    String fecha, 
    
    @Schema(description = "Fecha estimada de entrega", example = "2025-12-15")
    String fecha_entrega,
    
    @Schema(description = "Estado inicial", example = "PENDIENTE")
    String estado,
    
    @Schema(description = "ID del domicilio de entrega", example = "1")
    int domicilios_id,
    
    @Schema(description = "ID del pedido a enviar", example = "1")
    int pedidos_id
) {}