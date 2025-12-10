package com.TecNM_ITLP.API.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID; 

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.TecNM_ITLP.API.models.Pedido;
import com.TecNM_ITLP.API.dto.PedidoDTO;
import com.TecNM_ITLP.API.dto.PUTPedidoDTO;

@Repository
public class PedidoRepository {

    private final JdbcClient jdbcClient;

    public PedidoRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Pedido> findAll() {
        String sql = "SELECT * FROM pedidos";
        return jdbcClient.sql(sql).query(new PedidoRM()).list();
    }

    public Optional<Pedido> findById(int id) {
        String sql = "SELECT * FROM pedidos WHERE id = :id";
        return jdbcClient.sql(sql).param("id", id).query(new PedidoRM()).optional();
    }

    // Insertar Pedido
    public Pedido save(PedidoDTO dto) {
        // Generamos el UUID en Java
        String numeroUUID = UUID.randomUUID().toString();
        
        String sql = """
            INSERT INTO pedidos (fecha, numero, importe_productos, importe_envio, usuarios_id, metodos_pago_id, importe_iva, total) 
            VALUES (NOW(), :numero, :prod, :envio, :uid, :pago, :iva, :total) 
            RETURNING id
        """;
        
        Integer id = jdbcClient.sql(sql)
                .param("numero", numeroUUID)
                .param("prod", dto.importe_productos())
                .param("envio", dto.importe_envio())
                .param("uid", dto.usuarios_id())
                .param("pago", dto.metodos_pago_id())
                .param("iva", dto.importe_iva())
                .param("total", dto.total())
                .query((rs, rowNum) -> rs.getInt("id"))
                .single();

        return findById(id).orElse(null);
    }

    // Actualizar (Por ejemplo, para registrar que ya se pagó)
    public Pedido update(int id, PUTPedidoDTO dto) {
        String sql = """
            UPDATE pedidos 
            SET fecha_hora_pago = CAST(:fechaPago AS TIMESTAMP), metodos_pago_id = :pagoId
            WHERE id = :id 
            RETURNING id
        """;
        
        Optional<Integer> idActualizado = jdbcClient.sql(sql)
                .param("id", id)
                .param("fechaPago", dto.fecha_hora_pago())
                .param("pagoId", dto.metodos_pago_id())
                .query((rs, rowNum) -> rs.getInt("id"))
                .optional();

        return idActualizado.isPresent() ? findById(id).orElse(null) : null;
    }

    public void deleteById(int id) {
        jdbcClient.sql("DELETE FROM pedidos WHERE id = :id").param("id", id).update();
    }
}