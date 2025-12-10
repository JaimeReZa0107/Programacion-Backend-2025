package com.TecNM_ITLP.API.models;

import io.swagger.v3.oas.annotations.media.Schema;

public record Envio(
    @Schema(description = "ID del envío", example = "1")
    int id,
    
    @Schema(description = "Fecha estimada o real de entrega", example = "2025-12-15 10:00:00")
    String fecha_entrega,
    
    @Schema(description = "Fecha de salida del paquete", example = "2025-12-11 09:00:00")
    String fecha, 
    
    @Schema(description = "Estado actual", example = "EN_TRANSITO")
    String estado,
    
    @Schema(description = "Guía de rastreo (UUID)", example = "123e4567-e89b-12d3-a456-426614174000")
    String numero_seguimiento,
    
    @Schema(description = "ID de la dirección de destino", example = "5")
    int domicilios_id,
    
    @Schema(description = "ID del pedido asociado", example = "10")
    int pedidos_id
) {}