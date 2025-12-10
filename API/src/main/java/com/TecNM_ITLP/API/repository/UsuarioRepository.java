package com.TecNM_ITLP.API.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.TecNM_ITLP.API.models.Usuario;
import com.TecNM_ITLP.API.dto.UsuarioDTO;
import com.TecNM_ITLP.API.dto.PUTUsuarioDTO;

@Repository
public class UsuarioRepository {

    private final JdbcClient jdbcClient;

    public UsuarioRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Usuario> findAll() {
        String sql = "SELECT * FROM usuarios";
        return jdbcClient.sql(sql).query(new UsuarioRM()).list();
    }

    public Optional<Usuario> findById(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = :id";
        return jdbcClient.sql(sql).param("id", id).query(new UsuarioRM()).optional();
    }

    // Insertar
    public Usuario save(UsuarioDTO u) {
        // Nota: fecha_registro se llena sola en la BD si tiene default, si no, agrégala aquí.
        // OJO: En la imagen 'sexo' es text. Si usas ENUM en Postgres, agrega ::sexo_enum
        String sql = """
            INSERT INTO usuarios (nombre, email, telefono, sexo, fecha_nacimiento, contrasena, fecha_registro) 
            VALUES (:nombre, :email, :telefono, :sexo, CAST(:fecha_nacimiento AS DATE), :contrasena, NOW()) 
            RETURNING id
        """;
        
        Integer id = jdbcClient.sql(sql)
                .param("nombre", u.nombre())
                .param("email", u.email())
                .param("telefono", u.telefono()) // El driver maneja String a Character varying
                .param("sexo", u.sexo().name())  // Guardamos el nombre del ENUM
                .param("fecha_nacimiento", u.fecha_nacimiento())
                .param("contrasena", u.contrasena())
                .query((rs, rowNum) -> rs.getInt("id"))
                .single();

        return findById(id).orElse(null);
    }

    // Actualizar
    public Usuario update(int id, PUTUsuarioDTO u) {
        String sql = """
            UPDATE usuarios 
            SET nombre=:nombre, email=:email, telefono=:telefono, sexo=:sexo, fecha_nacimiento=CAST(:fecha_nacimiento AS DATE)
            WHERE id=:id 
            RETURNING id
        """;
        
        Optional<Integer> idActualizado = jdbcClient.sql(sql)
                .param("id", id)
                .param("nombre", u.nombre())
                .param("email", u.email())
                .param("telefono", u.telefono())
                .param("sexo", u.sexo().name())
                .param("fecha_nacimiento", u.fecha_nacimiento())
                .query((rs, rowNum) -> rs.getInt("id"))
                .optional();

        return idActualizado.isPresent() ? findById(id).orElse(null) : null;
    }

    public void deleteById(int id) {
        jdbcClient.sql("DELETE FROM usuarios WHERE id = :id").param("id", id).update();
    }
}