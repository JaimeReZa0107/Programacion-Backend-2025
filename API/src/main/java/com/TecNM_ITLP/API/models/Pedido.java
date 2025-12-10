package com.TecNM_ITLP.API.models;

import io.swagger.v3.oas.annotations.media.Schema;

public record Pedido(
    @Schema(description = "ID del pedido", example = "1")
    int id,
    
    @Schema(description = "Fecha de creación", example = "2025-12-10 14:00:00")
    String fecha,
    
    @Schema(description = "Número de pedido (UUID)", example = "a1b2c3d4-...")
    String numero,
    
    @Schema(description = "Total de los productos", example = "5000.00")
    Double importe_productos,
    
    @Schema(description = "Costo de envío", example = "150.00")
    Double importe_envio,
    
    @Schema(description = "ID del usuario", example = "1")
    int usuarios_id,
    
    @Schema(description = "ID del método de pago", example = "1")
    int metodos_pago_id,
    
    @Schema(description = "Fecha del pago", example = "2025-12-10 14:05:00")
    String fecha_hora_pago,
    
    @Schema(description = "IVA calculado (16%)", example = "800.00")
    Double importe_iva,
    
    @Schema(description = "Total final (Productos + Envío + IVA)", example = "5950.00")
    Double total
) {}