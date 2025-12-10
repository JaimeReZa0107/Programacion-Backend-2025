package com.TecNM_ITLP.API.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.TecNM_ITLP.API.models.Pedido;

public class PedidoRM implements RowMapper<Pedido> {
    
    @Override
    public Pedido mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        return new Pedido(
            rs.getInt("id"),
            rs.getString("fecha"),
            rs.getString("numero"), 
            rs.getDouble("importe_productos"),
            rs.getDouble("importe_envio"),
            rs.getInt("usuarios_id"),
            rs.getInt("metodos_pago_id"),
            rs.getString("fecha_hora_pago"),
            rs.getDouble("importe_iva"),
            rs.getDouble("total")
        );
    }
}