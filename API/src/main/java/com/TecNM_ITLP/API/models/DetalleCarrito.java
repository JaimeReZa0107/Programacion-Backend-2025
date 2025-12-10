package com.TecNM_ITLP.API.models;

import io.swagger.v3.oas.annotations.media.Schema;

public record DetalleCarrito(
    @Schema(description = "ID único de la línea del carrito", example = "1")
    int id,
    
    @Schema(description = "Cantidad de productos", example = "2")
    int cantidad,
    
    @Schema(description = "Precio total (Unitario * Cantidad)", example = "25500.00")
    Double precio, // Usamos Double porque en BD es 'numeric'
    
    @Schema(description = "ID del producto", example = "5")
    int productos_id,
    
    @Schema(description = "ID del usuario dueño del carrito", example = "1")
    int usuarios_id
) {}