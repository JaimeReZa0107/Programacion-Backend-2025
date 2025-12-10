package com.TecNM_ITLP.API.models;

public record Pedido(
    int id,
    String fecha,           
    String numero,          
    double importe_productos,
    double importe_envio,
    int usuarios_id,
    int metodos_pago_id,
    String fecha_hora_pago, 
    double importe_iva,
    double total
) {}