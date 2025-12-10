package com.TecNM_ITLP.API.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID; // Para generar la guía de rastreo

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.TecNM_ITLP.API.models.Envio;
import com.TecNM_ITLP.API.dto.EnvioDTO;
import com.TecNM_ITLP.API.dto.PUTEnvioDTO;

@Repository
public class EnvioRepository {

    private final JdbcClient jdbcClient;

    public EnvioRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Envio> findAll() {
        String sql = "SELECT * FROM envios";
        return jdbcClient.sql(sql).query(new EnvioRM()).list();
    }

    public Optional<Envio> findById(int id) {
        String sql = "SELECT * FROM envios WHERE id = :id";
        return jdbcClient.sql(sql).param("id", id).query(new EnvioRM()).optional();
    }

    // Insertar Envío
    public Envio save(EnvioDTO dto) {
        // Generamos guía automática
        String guia = UUID.randomUUID().toString();

      
        String sql = """
            INSERT INTO envios (fecha, fecha_entrega, estado, numero_seguimiento, domicilios_id, pedidos_id) 
            VALUES (CAST(:fecha AS TIMESTAMP), CAST(:fecha_entrega AS TIMESTAMP), :estado, :guia, :dom, :ped) 
            RETURNING id
        """;
        
        Integer id = jdbcClient.sql(sql)
                .param("fecha", dto.fecha())
                .param("fecha_entrega", dto.fecha_entrega())
                .param("estado", dto.estado())
                .param("guia", guia)
                .param("dom", dto.domicilios_id())
                .param("ped", dto.pedidos_id())
                .query((rs, rowNum) -> rs.getInt("id"))
                .single();

        return findById(id).orElse(null);
    }

    // Actualizar Estado
    public Envio update(int id, PUTEnvioDTO dto) {
        String sql = """
            UPDATE envios 
            SET fecha_entrega = CAST(:fecha_entrega AS TIMESTAMP), estado = :estado 
            WHERE id = :id 
            RETURNING id
        """;
        
        Optional<Integer> idActualizado = jdbcClient.sql(sql)
                .param("id", id)
                .param("fecha_entrega", dto.fecha_entrega())
                .param("estado", dto.estado())
                .query((rs, rowNum) -> rs.getInt("id"))
                .optional();

        return idActualizado.isPresent() ? findById(id).orElse(null) : null;
    }

    public void deleteById(int id) {
        jdbcClient.sql("DELETE FROM envios WHERE id = :id").param("id", id).update();
    }
}