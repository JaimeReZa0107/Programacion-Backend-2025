package com.TecNM_ITLP.API.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PedidoDTO(
    @Schema(description = "ID del usuario que compra", example = "1")
    int usuarios_id,
    
    @Schema(description = "Costo de envío seleccionado", example = "150.00")
    Double importe_envio,
    
    @Schema(description = "ID del método de pago", example = "1")
    int metodos_pago_id
) {}