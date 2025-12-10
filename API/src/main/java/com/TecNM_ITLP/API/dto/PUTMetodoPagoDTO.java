package com.TecNM_ITLP.API.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PUTMetodoPagoDTO(
    @Schema(description = "Nombre del método", example = "PayPal")
    String nombre,
    
    @Schema(description = "Comisión ajustada", example = "18.00")
    Double comision
) 
{
    
}