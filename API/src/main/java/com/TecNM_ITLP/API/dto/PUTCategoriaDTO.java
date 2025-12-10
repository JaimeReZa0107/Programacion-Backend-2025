package com.TecNM_ITLP.API.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PUTCategoriaDTO(
     @Schema(description = "Nombre de la categoria", example = "Telefonía")
    String nombre) 
{
    
}
