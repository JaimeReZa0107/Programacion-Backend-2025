package com.TecNM_ITLP.API.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.TecNM_ITLP.API.dto.PUTProductoDTO;
import com.TecNM_ITLP.API.dto.ProductoDTO;
import com.TecNM_ITLP.API.models.Producto;

@Repository
public class ProductoRepository {

    private final JdbcClient jdbcClient;

    public ProductoRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // 1. Obtener todos 
    public List<Producto> findAll() {
        String sql = "SELECT * FROM productos"; 
        return jdbcClient.sql(sql)
                .query(new ProductoRM()) // <--- Aquí usamos tu traductor
                .list();
    }

    // 2. Obtener por ID
    public Optional<Producto> findById(int id) {
        String sql = "SELECT * FROM productos WHERE id = :id";
        return jdbcClient.sql(sql)
                .param("id", id)
                .query(new ProductoRM()) // <--- Reutilizamos el traductor
                .optional();
    }

    // 3. Insertar
    public Producto save(ProductoDTO p) {
        String sql = """
                INSERT INTO productos(nombre, precio, sku, color, marca, descripcion, peso, alto, ancho, profundidad, categorias_id) 
                VALUES (:nombre, :precio, :sku, :color, :marca, :descripcion, :peso, :alto, :ancho, :profundidad, :catId) 
                RETURNING id
                """;
        
        Integer idGenerado = jdbcClient.sql(sql)
            .param("nombre", p.nombre())
            .param("precio", p.precio())
            .param("sku", p.sku())
            .param("color", p.color())
            .param("marca", p.marca())
            .param("descripcion", p.descripcion())
            .param("peso", p.peso())
            .param("alto", p.alto())
            .param("ancho", p.ancho())
            .param("profundidad", p.profundidad())
            .param("catId", p.categorias_id())
            .query((rs, rowNum) -> rs.getInt("id"))
            .single();

        return findById(idGenerado).orElse(null);
    }

    // 4. Actualizar
    public Producto update(int id, PUTProductoDTO p) {
        String sql = """
                UPDATE productos 
                SET nombre=:nombre, precio=:precio, sku=:sku, color=:color, marca=:marca, descripcion=:descripcion, peso=:peso, alto=:alto, ancho=:ancho, profundidad=:profundidad 
                WHERE id=:id 
                RETURNING id
                """;
        
        Optional<Integer> idActualizado = jdbcClient.sql(sql)
            .param("id", id)
            .param("nombre", p.nombre())
            .param("precio", p.precio())
            .param("sku", p.sku())
            .param("color", p.color())
            .param("marca", p.marca())
            .param("descripcion", p.descripcion())
            .param("peso", p.peso())
            .param("alto", p.alto())
            .param("ancho", p.ancho())
            .param("profundidad", p.profundidad())
            .query((rs, rowNum) -> rs.getInt("id"))
            .optional();

        return idActualizado.isPresent() ? findById(id).orElse(null) : null;
    }

    // 5. Eliminar 
    public void deleteById(int id) {
        String sql = "DELETE FROM productos WHERE id = :id";
        jdbcClient.sql(sql)
            .param("id", id)
            .update();
    }
}