package com.TecNM_ITLP.API.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; 
import com.TecNM_ITLP.API.dto.PUTProductoDTO;
import com.TecNM_ITLP.API.dto.ProductoDTO;
import com.TecNM_ITLP.API.models.Producto;
import com.TecNM_ITLP.API.repository.ProductoRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/productos")
@Tag(name = "Productos", description = "Catálogo de productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository; 

    // 1. Obtener todos
    @GetMapping()
    public ResponseEntity<List<Producto>> obtenerProductos() {
        List<Producto> resultado = productoRepository.findAll();
        return ResponseEntity.ok(resultado);
    }

    // 2. Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable int id) {
        return productoRepository.findById(id)
                .map(producto -> ResponseEntity.ok(producto)) // Si existe, devuelve 200 OK
                .orElse(ResponseEntity.notFound().build());   // Si no, devuelve 404 Not Found
    }

    // 3. Insertar
    @PostMapping
    public ResponseEntity<?> insertarProducto(@RequestBody ProductoDTO productoDTO) {
        try {
            Producto productoInsertado = productoRepository.save(productoDTO);
            return ResponseEntity.status(201).body(productoInsertado);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear el producto: " + e.getMessage());
        }
    }

    // 4. Eliminar 
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable int id) {
        // Primero verificamos si existe
        if (productoRepository.findById(id).isPresent()) {
            productoRepository.deleteById(id);
            return ResponseEntity.ok().build(); // 200 OK (sin cuerpo porque ya no existe)
        } else {
            return ResponseEntity.notFound().build(); // 404 si no existía
        }
    }

    // 5. Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable int id, @RequestBody PUTProductoDTO productoDTO) {
        Producto productoActualizado = productoRepository.update(id, productoDTO);
        
        if (productoActualizado != null) {
            return ResponseEntity.ok(productoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}