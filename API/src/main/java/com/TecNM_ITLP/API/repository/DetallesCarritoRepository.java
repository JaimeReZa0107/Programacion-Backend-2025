package com.TecNM_ITLP.API.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.TecNM_ITLP.API.models.DetalleCarrito;
import com.TecNM_ITLP.API.dto.DetalleCarritoDTO;
import com.TecNM_ITLP.API.dto.PUTDetalleCarritoDTO;

@Repository
public class DetallesCarritoRepository {

    private final JdbcClient jdbcClient;

    public DetallesCarritoRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // 1. Obtener todos
    public List<DetalleCarrito> findAll() {
        String sql = "SELECT * FROM detalles_carrito";
        return jdbcClient.sql(sql).query(new DetalleCarritoRM()).list();
    }

    // 2. Obtener por ID
    public Optional<DetalleCarrito> findById(int id) {
        String sql = "SELECT * FROM detalles_carrito WHERE id = :id";
        return jdbcClient.sql(sql).param("id", id).query(new DetalleCarritoRM()).optional();
    }

    // 3. Obtener carrito de un usuario específico (MUY IMPORTANTE)
    public List<DetalleCarrito> findByUsuarioId(int usuarioId) {
        String sql = "SELECT * FROM detalles_carrito WHERE usuarios_id = :uid";
        return jdbcClient.sql(sql).param("uid", usuarioId).query(new DetalleCarritoRM()).list();
    }

    // 4. Agregar al carrito (Calculando precio automáticamente)
    public DetalleCarrito save(DetalleCarritoDTO dto) {
        // A) Obtenemos el precio unitario desde la tabla 'productos'
        String sqlPrecio = "SELECT precio FROM productos WHERE id = :pid";
        Double precioUnitario = jdbcClient.sql(sqlPrecio)
                .param("pid", dto.productos_id())
                .query(Double.class) 
                .single();

        // B) Calculamos el total
        Double precioTotal = precioUnitario * dto.cantidad();

        // C) Insertamos
        String sql = """
            INSERT INTO detalles_carrito (cantidad, precio, productos_id, usuarios_id) 
            VALUES (:cantidad, :precio, :pid, :uid) 
            RETURNING id
        """;
        
        Integer id = jdbcClient.sql(sql)
                .param("cantidad", dto.cantidad())
                .param("precio", precioTotal)
                .param("pid", dto.productos_id())
                .param("uid", dto.usuarios_id())
                .query((rs, rowNum) -> rs.getInt("id"))
                .single();

        return findById(id).orElse(null);
    }

    // 5. Actualizar cantidad (Recalcula el precio)
    public DetalleCarrito update(int id, PUTDetalleCarritoDTO dto) {
        // A) Obtenemos precio unitario de nuevo
        String sqlPrecio = "SELECT precio FROM productos WHERE id = :pid";
        Double precioUnitario = jdbcClient.sql(sqlPrecio)
                .param("pid", dto.productos_id())
                .query(Double.class)
                .single();
        
        Double nuevoTotal = precioUnitario * dto.cantidad();

        // B) Actualizamos cantidad y precio total
        String sql = """
            UPDATE detalles_carrito 
            SET cantidad = :cant, precio = :precio 
            WHERE id = :id 
            RETURNING id
        """;
        
        Optional<Integer> idActualizado = jdbcClient.sql(sql)
                .param("id", id)
                .param("cant", dto.cantidad())
                .param("precio", nuevoTotal)
                .query((rs, rowNum) -> rs.getInt("id"))
                .optional();

        return idActualizado.isPresent() ? findById(id).orElse(null) : null;
    }

    // 6. Eliminar un item específico
    public void deleteById(int id) {
        jdbcClient.sql("DELETE FROM detalles_carrito WHERE id = :id").param("id", id).update();
    }

    // 7. Vaciar todo el carrito de un usuario
    public void deleteByUsuarioId(int usuarioId) {
        jdbcClient.sql("DELETE FROM detalles_carrito WHERE usuarios_id = :uid").param("uid", usuarioId).update();
    }
}