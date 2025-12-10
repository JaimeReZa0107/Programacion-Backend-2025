package com.TecNM_ITLP.API.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PedidoDTO(
    @Schema(description = "Monto total de los productos", example = "1500.50")
    double importe_productos,
    
    @Schema(description = "Costo de envío", example = "150.00")
    double importe_envio,
    
    @Schema(description = "ID del usuario que compra", example = "1")
    int usuarios_id,
    
    @Schema(description = "ID del método de pago (Ej: 1=Tarjeta, 2=Paypal)", example = "1")
    int metodos_pago_id,
    
    @Schema(description = "Monto calculado de IVA", example = "240.08")
    double importe_iva,
    
    @Schema(description = "Total final a pagar (Productos + Envío + IVA)", example = "1890.58")
    double total
) {}