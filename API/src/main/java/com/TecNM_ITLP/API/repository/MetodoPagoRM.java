package com.TecNM_ITLP.API.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.TecNM_ITLP.API.models.MetodoPago;

public class MetodoPagoRM implements RowMapper<MetodoPago> {
    
    @Override
    public MetodoPago mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        return new MetodoPago(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getDouble("comision")
        );
    }
}