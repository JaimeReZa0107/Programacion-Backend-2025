package com.TecNM_ITLP.API.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.TecNM_ITLP.API.models.Usuario;
import com.TecNM_ITLP.API.models.Sexo;

public class UsuarioRM implements RowMapper<Usuario> {
    
    @Override
    public Usuario mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        // Conversión especial para Telefono (String BD -> char[] Modelo)
        String telString = rs.getString("telefono");
        char[] telChars = (telString != null) ? telString.toCharArray() : new char[0];

        // Conversión especial para Fechas (Date/Timestamp BD -> String Modelo)
        String fechaNac = rs.getString("fecha_nacimiento"); // JDBC lo convierte a String automáticamente
        String fechaReg = rs.getString("fecha_registro");

        return new Usuario(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("email"),
            telChars,
            Sexo.valueOf(rs.getString("sexo")), // Asume que en BD dice "MASCULINO"/"FEMENINO"
            fechaNac,
            rs.getString("contrasena"),
            fechaReg
        );
    }
}