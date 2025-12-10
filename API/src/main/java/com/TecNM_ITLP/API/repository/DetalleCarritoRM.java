package com.TecNM_ITLP.API.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.TecNM_ITLP.API.models.DetallesCarrito;

public class DetallesCarritoRM implements RowMapper<DetallesCarrito> {
    @Override
    public DetallesCarrito mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        return new DetallesCarrito(
            rs.getInt("id"),
            rs.getInt("cantidad"),
            rs.getDouble("precio"),
            rs.getInt("productos_id"),
            rs.getInt("usuarios_id")
        );
    }
}