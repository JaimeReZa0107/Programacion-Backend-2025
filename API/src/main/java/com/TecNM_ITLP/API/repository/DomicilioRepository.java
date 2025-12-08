package com.TecNM_ITLP.API.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.TecNM_ITLP.API.models.Domicilio;
import com.TecNM_ITLP.API.dto.DomicilioDTO;
import com.TecNM_ITLP.API.dto.PUTDomicilioDTO;

@Repository
public class DomicilioRepository {

    private final JdbcClient jdbcClient;

    public DomicilioRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // 1. Obtener todos
    public List<Domicilio> findAll() {
        String sql = "SELECT * FROM direcciones";
        return jdbcClient.sql(sql)
                .query(new DomicilioRM())
                .list();
    }

    // 2. Obtener por ID
    public Optional<Domicilio> findById(int id) {
        String sql = "SELECT * FROM direcciones WHERE id = :id";
        return jdbcClient.sql(sql)
                .param("id", id)
                .query(new DomicilioRM())
                .optional();
    }

    // 3. Insertar
    // Nota: Convertimos el char[] o String del DTO a String para la BD, ya que JDBC prefiere Strings
    public Domicilio save(DomicilioDTO d) {
        String sql = """
                INSERT INTO direcciones (calle, numero, colonia, cp, estado, ciudad, usuarios_id) 
                VALUES (:calle, :numero, :colonia, :cp, :estado, :ciudad, :usuarios_id) 
                RETURNING id
                """;
        
        // Asumimos que tu DTO recibe el CP como String (es lo normal en JSON)
        Integer idGenerado = jdbcClient.sql(sql)
                .param("calle", d.calle())
                .param("numero", d.numero())
                .param("colonia", d.colonia())
                .param("cp", String.valueOf(d.cp())) // Convertimos a String para enviar a la BD
                .param("estado", d.estado())
                .param("ciudad", d.ciudad())
                .param("usuarios_id", d.usuarios_id())
                .query((rs, rowNum) -> rs.getInt("id"))
                .single();

        return findById(idGenerado).orElse(null);
    }

    // 4. Actualizar
    public Domicilio update(int id, PUTDomicilioDTO d) {
        String sql = """
                UPDATE direcciones 
                SET calle=:calle, numero=:numero, colonia=:colonia, cp=:cp, estado=:estado, ciudad=:ciudad
                WHERE id=:id 
                RETURNING id
                """;
        
        Optional<Integer> idActualizado = jdbcClient.sql(sql)
                .param("id", id)
                .param("calle", d.calle())
                .param("numero", d.numero())
                .param("colonia", d.colonia())
                .param("cp", String.valueOf(d.cp()))
                .param("estado", d.estado())
                .param("ciudad", d.ciudad())
                .query((rs, rowNum) -> rs.getInt("id"))
                .optional();

        return idActualizado.isPresent() ? findById(id).orElse(null) : null;
    }

    // 5. Eliminar
    public void deleteById(int id) {
        String sql = "DELETE FROM direcciones WHERE id = :id";
        jdbcClient.sql(sql)
                .param("id", id)
                .update();
    }
}