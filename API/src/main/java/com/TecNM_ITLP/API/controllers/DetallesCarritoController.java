package com.TecNM_ITLP.API.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// SWAGGER
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.TecNM_ITLP.API.models.DetalleCarrito;
import com.TecNM_ITLP.API.repository.DetallesCarritoRepository;
import com.TecNM_ITLP.API.dto.DetalleCarritoDTO;
import com.TecNM_ITLP.API.dto.PUTDetalleCarritoDTO;

@RestController
@RequestMapping("/detalles_carrito")
@Tag(name = "Carrito de Compras", description = "Operaciones para gestionar la canasta de compra")
public class DetallesCarritoController {

    @Autowired
    private DetallesCarritoRepository repository;

    @GetMapping
    @Operation(summary = "Ver todos los items", description = "Lista todos los productos en todos los carritos (Admin)")
    public ResponseEntity<List<DetalleCarrito>> obtenerTodos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar item por ID", description = "Obtiene un detalle específico del carrito")
    public ResponseEntity<DetalleCarrito> obtenerPorId(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Ver carrito de un usuario", description = "Obtiene todos los productos que un usuario tiene en su canasta")
    public ResponseEntity<List<DetalleCarrito>> obtenerPorUsuario(@PathVariable int usuarioId) {
        return ResponseEntity.ok(repository.findByUsuarioId(usuarioId));
    }

    @PostMapping
    @Operation(summary = "Agregar al carrito", description = "Añade un producto y calcula su precio total automáticamente")
    public ResponseEntity<?> agregarAlCarrito(@RequestBody DetalleCarritoDTO dto) {
        try {
            DetalleCarrito nuevo = repository.save(dto);
            return ResponseEntity.status(201).body(nuevo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al agregar: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cantidad", description = "Modifica la cantidad de un producto y recalcula el precio")
    public ResponseEntity<DetalleCarrito> actualizarCantidad(@PathVariable int id, @RequestBody PUTDetalleCarritoDTO dto) {
        DetalleCarrito actualizado = repository.update(id, dto);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar item", description = "Quita un producto específico del carrito")
    public ResponseEntity<Void> eliminarItem(@PathVariable int id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/usuario/{usuarioId}")
    @Operation(summary = "Vaciar carrito", description = "Elimina TODOS los productos del carrito de un usuario")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable int usuarioId) {
        repository.deleteByUsuarioId(usuarioId);
        return ResponseEntity.ok().build();
    }
}