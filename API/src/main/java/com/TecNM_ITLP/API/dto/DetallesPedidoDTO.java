package com.TecNM_ITLP.API.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record DetallesPedidoDTO(
    @Schema(description = "ID del pedido", example = "1")
    int pedidos_id,
    
    @Schema(description = "ID del producto", example = "5")
    int productos_id,
    
    @Schema(description = "Cantidad", example = "2")
    int cantidad,
    
    @Schema(description = "Precio pactado", example = "5500.00")
    Double precio
) {}