package com.TecNM_ITLP.API.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record MetodoPagoDTO(
    @Schema(description = "Nombre del método", example = "Tarjeta de Crédito")
    String nombre,
    
    @Schema(description = "Comisión cobrada por la pasarela", example = "15.50")
    Double comision
) 
{
    
}