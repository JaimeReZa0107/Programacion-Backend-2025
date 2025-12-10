package com.TecNM_ITLP.API.models;

import io.swagger.v3.oas.annotations.media.Schema;

public record DetallesPedido(
    @Schema(description = "ID único del detalle", example = "100")
    int id,
    
    @Schema(description = "Cantidad comprada", example = "2")
    int cantidad,
    
    @Schema(description = "Precio unitario congelado al momento de la compra", example = "5500.00")
    Double precio,
    
    @Schema(description = "ID del producto", example = "5")
    int productos_id,
    
    @Schema(description = "ID del pedido al que pertenece", example = "10")
    int pedidos_id
) {}