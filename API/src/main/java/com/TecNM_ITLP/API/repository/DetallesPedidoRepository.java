package com.TecNM_ITLP.API.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.TecNM_ITLP.API.models.DetallesPedido;
import com.TecNM_ITLP.API.dto.DetallesPedidoDTO;
import com.TecNM_ITLP.API.dto.PUTDetallesPedidoDTO;

@Repository
public class DetallesPedidoRepository {

    private final JdbcClient jdbcClient;

    public DetallesPedidoRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<DetallesPedido> findAll() {
        String sql = "SELECT * FROM detalles_pedido";
        return jdbcClient.sql(sql).query(new DetallesPedidoRM()).list();
    }

    public Optional<DetallesPedido> findById(int id) {
        String sql = "SELECT * FROM detalles_pedido WHERE id = :id";
        return jdbcClient.sql(sql).param("id", id).query(new DetallesPedidoRM()).optional();
    }

   
    public List<DetallesPedido> findByPedidoId(int pedidoId) {
        String sql = "SELECT * FROM detalles_pedido WHERE pedidos_id = :pid";
        return jdbcClient.sql(sql).param("pid", pedidoId).query(new DetallesPedidoRM()).list();
    }

    // Insertar
    public DetallesPedido save(DetallesPedidoDTO dto) {
        String sql = """
            INSERT INTO detalles_pedido (pedidos_id, productos_id, cantidad, precio) 
            VALUES (:pid, :prod, :cant, :precio) 
            RETURNING id
        """;
        
        Integer id = jdbcClient.sql(sql)
                .param("pid", dto.pedidos_id())
                .param("prod", dto.productos_id())
                .param("cant", dto.cantidad())
                .param("precio", dto.precio())
                .query((rs, rowNum) -> rs.getInt("id"))
                .single();

        return findById(id).orElse(null);
    }

    // Actualizar
    public DetallesPedido update(int id, PUTDetallesPedidoDTO dto) {
        String sql = """
            UPDATE detalles_pedido 
            SET cantidad = :cant, precio = :precio 
            WHERE id = :id 
            RETURNING id
        """;
        
        Optional<Integer> idActualizado = jdbcClient.sql(sql)
                .param("id", id)
                .param("cant", dto.cantidad())
                .param("precio", dto.precio())
                .query((rs, rowNum) -> rs.getInt("id"))
                .optional();

        return idActualizado.isPresent() ? findById(id).orElse(null) : null;
    }

    // Eliminar
    public void deleteById(int id) {
        jdbcClient.sql("DELETE FROM detalles_pedido WHERE id = :id").param("id", id).update();
    }
}