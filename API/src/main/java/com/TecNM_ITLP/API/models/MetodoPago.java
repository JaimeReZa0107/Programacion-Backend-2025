package com.TecNM_ITLP.API.models;

import io.swagger.v3.oas.annotations.media.Schema;

public record MetodoPago(
    @Schema(description = "Identificador único del método de pago", example = "1")
    int id,

    @Schema(description = "Nombre comercial del método", example = "Tarjeta de Crédito")
    String nombre,

    @Schema(description = "Comisión aplicada por transacción", example = "3.50")
    Double comision
) 
{
    
}