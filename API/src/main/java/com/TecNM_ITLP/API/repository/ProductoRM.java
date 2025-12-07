package com.TecNM_ITLP.API.repository;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.TecNM_ITLP.API.models.Producto;

// Este archivo solo sirve para TRADUCIR de SQL a Java
public class ProductoRM implements RowMapper<Producto> {
    @Override
    public Producto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Producto(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getFloat("precio"),
            rs.getString("sku"),
            rs.getString("color"),
            rs.getString("marca"),
            rs.getString("descripcion"),
            rs.getFloat("peso"),
            rs.getFloat("alto"),
            rs.getFloat("ancho"),
            rs.getFloat("profundidad"),
            rs.getInt("categorias_id") //en la BD es categorias_id
        );
    }
}