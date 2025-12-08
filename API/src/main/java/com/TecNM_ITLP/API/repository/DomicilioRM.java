package com.TecNM_ITLP.API.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.TecNM_ITLP.API.models.Domicilio;

public class DomicilioRM implements RowMapper<Domicilio> {
    
    @Override
    public Domicilio mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        // Obtenemos el CP como String primero
        String cpString = rs.getString("cp");
        
        // Convertimos a char[] (manejando posible nulo para que no explote)
        char[] cpChars = (cpString != null) ? cpString.toCharArray() : new char[0];

        return new Domicilio(
            rs.getInt("id"),
            rs.getString("calle"),
            rs.getString("numero"),
            rs.getString("colonia"),
            cpChars, // Aquí pasamos el char[]
            rs.getString("estado"),
            rs.getString("ciudad"),
            rs.getInt("usuarios_id")
        );
    }
}