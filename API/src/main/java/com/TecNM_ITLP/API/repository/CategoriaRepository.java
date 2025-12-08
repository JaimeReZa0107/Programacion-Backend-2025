package com.TecNM_ITLP.API.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.TecNM_ITLP.API.models.Categoria;
import com.TecNM_ITLP.API.dto.CategoriaDTO;
import com.TecNM_ITLP.API.dto.PUTCategoriaDTO;

@Repository
public class CategoriaRepository {

    private final JdbcClient jdbcClient;

    public CategoriaRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // 1. Obtener Todas
    public List<Categoria> findAll() {
        String sql = "SELECT id, nombre FROM categorias";
        return jdbcClient.sql(sql)
                .query(new CategoriaRM()) 
                .list();
    }

    // 2. Obtener por ID
    public Optional<Categoria> findById(int id) {
        String sql = "SELECT id, nombre FROM categorias WHERE id = :id";
        return jdbcClient.sql(sql)
                .param("id", id)
                .query(new CategoriaRM())
                .optional();
    }

    // 3. Insertar
    public Categoria save(CategoriaDTO c) {
        String sql = "INSERT INTO categorias (nombre) VALUES (:nombre) RETURNING id, nombre";
        return jdbcClient.sql(sql)
                .param("nombre", c.nombre())
                .query(new CategoriaRM()) 
                .single();
    }

    // 4. Actualizar
    public Categoria update(int id, PUTCategoriaDTO c) { 
    String sql = "UPDATE categorias SET nombre = :nombre WHERE id = :id RETURNING id, nombre";
    try {
        return jdbcClient.sql(sql)
                .param("id", id)
                .param("nombre", c.nombre()) // Ambos DTOs tienen el método .nombre()
                .query(new CategoriaRM())
                .single();
    } catch (Exception e) {
        return null;
    }
}

    // 5. Eliminar (Físico)
    public void deleteById(int id) {
        String sql = "DELETE FROM categorias WHERE id = :id";
        jdbcClient.sql(sql)
                .param("id", id)
                .update();
    }
}