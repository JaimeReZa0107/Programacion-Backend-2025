package com.TecNM_ITLP.API.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import java.sql.ResultSet;
import java.sql.SQLException;

// Importamos TU modelo
import com.TecNM_ITLP.API.models.Categoria;

public class CategoriaRM implements RowMapper<Categoria> {
    
    @Override
    public Categoria mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        // Simplemente llenamos el record con los datos de la columna
        return new Categoria(
                rs.getInt("id"),
                rs.getString("nombre")
        );
    }
}