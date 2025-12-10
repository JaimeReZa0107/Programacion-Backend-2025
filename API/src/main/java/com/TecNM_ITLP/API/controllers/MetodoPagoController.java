package com.TecNM_ITLP.API.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// SWAGGER
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.TecNM_ITLP.API.models.MetodoPago;
import com.TecNM_ITLP.API.repository.MetodoPagoRepository;
import com.TecNM_ITLP.API.dto.MetodoPagoDTO;
import com.TecNM_ITLP.API.dto.PUTMetodoPagoDTO;

@RestController
@RequestMapping("/metodos_pago")
@Tag(name = "Métodos de Pago", description = "Catálogo de formas de pago aceptadas (Tarjetas, Efectivo, etc.)")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoRepository repository;

    @GetMapping
    @Operation(summary = "Listar métodos de pago", description = "Obtiene todas las opciones de pago disponibles")
    public ResponseEntity<List<MetodoPago>> obtenerMetodos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar método por ID", description = "Obtiene el detalle de una forma de pago")
    public ResponseEntity<MetodoPago> obtenerMetodoPorId(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear método de pago", description = "Registra una nueva opción de pago en el sistema")
    public ResponseEntity<?> insertarMetodo(@RequestBody MetodoPagoDTO dto) {
        try {
            MetodoPago nuevo = repository.save(dto);
            return ResponseEntity.status(201).body(nuevo);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar método", description = "Modifica el nombre o la comisión de un método de pago")
    public ResponseEntity<MetodoPago> actualizarMetodo(@PathVariable int id, @RequestBody PUTMetodoPagoDTO dto) {
        MetodoPago actualizado = repository.update(id, dto);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar método", description = "Elimina una opción de pago del catálogo")
    public ResponseEntity<Void> eliminarMetodo(@PathVariable int id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}