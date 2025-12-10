package com.TecNM_ITLP.API.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.TecNM_ITLP.API.models.DetallesPedido;

public class DetallesPedidoRM implements RowMapper<DetallesPedido> {
    @Override
    public DetallesPedido mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        return new DetallesPedido(
            rs.getInt("id"),
            rs.getInt("cantidad"),
            rs.getDouble("precio"),
            rs.getInt("productos_id"),
            rs.getInt("pedidos_id")
        );
    }
}