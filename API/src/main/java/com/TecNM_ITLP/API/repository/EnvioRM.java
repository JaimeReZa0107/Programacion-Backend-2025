package com.TecNM_ITLP.API.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.TecNM_ITLP.API.models.Envio;

public class EnvioRM implements RowMapper<Envio> {
    @Override
    public Envio mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        return new Envio(
            rs.getInt("id"),
            rs.getString("fecha_entrega"),
            rs.getString("fecha"),
            rs.getString("estado"),
            rs.getString("numero_seguimiento"),
            rs.getInt("domicilios_id"),
            rs.getInt("pedidos_id")
        );
    }
}