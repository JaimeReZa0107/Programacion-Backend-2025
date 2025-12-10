package com.TecNM_ITLP.API.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.TecNM_ITLP.API.models.Envio;
import com.TecNM_ITLP.API.repository.EnvioRepository;
import com.TecNM_ITLP.API.dto.EnvioDTO;
import com.TecNM_ITLP.API.dto.PUTEnvioDTO;

@RestController
@RequestMapping("/envios")
@Tag(name = "Logística de Envíos", description = "Gestión de guías, fechas de entrega y estatus de paquetes")
public class EnvioController {

    @Autowired
    private EnvioRepository repository;

    @GetMapping
    @Operation(summary = "Listar envíos", description = "Obtiene todos los envíos registrados")
    public ResponseEntity<List<Envio>> obtenerEnvios() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar envío por ID", description = "Obtiene detalles de rastreo de un paquete")
    public ResponseEntity<Envio> obtenerEnvioPorId(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear envío", description = "Genera un registro de envío para un pedido existente y crea una guía de rastreo automática")
    public ResponseEntity<?> insertarEnvio(@RequestBody EnvioDTO dto) {
        try {
            Envio nuevo = repository.save(dto);
            return ResponseEntity.status(201).body(nuevo);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear envío: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar estado", description = "Modifica el estatus (ej: ENTREGADO) o la fecha de entrega")
    public ResponseEntity<Envio> actualizarEnvio(@PathVariable int id, @RequestBody PUTEnvioDTO dto) {
        Envio actualizado = repository.update(id, dto);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar envío", description = "Borra un registro de envío del sistema")
    public ResponseEntity<Void> eliminarEnvio(@PathVariable int id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}