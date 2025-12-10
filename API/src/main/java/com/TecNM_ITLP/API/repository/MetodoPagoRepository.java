package com.TecNM_ITLP.API.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.TecNM_ITLP.API.models.MetodoPago;
import com.TecNM_ITLP.API.dto.MetodoPagoDTO;
import com.TecNM_ITLP.API.dto.PUTMetodoPagoDTO;

@Repository
public class MetodoPagoRepository {

    private final JdbcClient jdbcClient;

    public MetodoPagoRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<MetodoPago> findAll() {
        String sql = "SELECT * FROM metodos_pago";
        return jdbcClient.sql(sql).query(new MetodoPagoRM()).list();
    }

    public Optional<MetodoPago> findById(int id) {
        String sql = "SELECT * FROM metodos_pago WHERE id = :id";
        return jdbcClient.sql(sql).param("id", id).query(new MetodoPagoRM()).optional();
    }

    public MetodoPago save(MetodoPagoDTO dto) {
        String sql = """
            INSERT INTO metodos_pago (nombre, comision) 
            VALUES (:nombre, :comision) 
            RETURNING id
        """;
        
        Integer id = jdbcClient.sql(sql)
                .param("nombre", dto.nombre())
                .param("comision", dto.comision())
                .query((rs, rowNum) -> rs.getInt("id"))
                .single();

        return findById(id).orElse(null);
    }

    public MetodoPago update(int id, PUTMetodoPagoDTO dto) {
        String sql = """
            UPDATE metodos_pago 
            SET nombre = :nombre, comision = :comision 
            WHERE id = :id 
            RETURNING id
        """;
        
        Optional<Integer> idActualizado = jdbcClient.sql(sql)
                .param("id", id)
                .param("nombre", dto.nombre())
                .param("comision", dto.comision())
                .query((rs, rowNum) -> rs.getInt("id"))
                .optional();

        return idActualizado.isPresent() ? findById(id).orElse(null) : null;
    }

    public void deleteById(int id) {
        jdbcClient.sql("DELETE FROM metodos_pago WHERE id = :id").param("id", id).update();
    }
}