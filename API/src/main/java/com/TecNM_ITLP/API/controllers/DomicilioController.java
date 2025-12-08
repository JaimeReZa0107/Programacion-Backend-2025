package com.TecNM_ITLP.API.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Importa todo (Get, Post, Put, Delete)

// IMPORTACIONES DE TU PROYECTO
import com.TecNM_ITLP.API.models.Domicilio;
import com.TecNM_ITLP.API.repository.DomicilioRepository; // Usamos tu Repository
import com.TecNM_ITLP.API.dto.DomicilioDTO;
import com.TecNM_ITLP.API.dto.PUTDomicilioDTO; // Usamos el DTO de actualización

@RestController
@RequestMapping("/domicilios") // La URL base
public class DomicilioController {

    @Autowired
    private DomicilioRepository repository; // Inyectamos TU repositorio

    // 1. Obtener todos
    @GetMapping()
    public ResponseEntity<List<Domicilio>> obtenerDomicilios() {
        // Tu repositorio usa el método estándar findAll()
        return ResponseEntity.ok(repository.findAll());
    }

    // 2. Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Domicilio> obtenerDomicilioPorId(@PathVariable int id) {
        // Usamos .map() para manejar el Optional de forma elegante
        return repository.findById(id)
                .map(domicilio -> ResponseEntity.ok(domicilio))
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Insertar
    @PostMapping
    public ResponseEntity<?> insertarDomicilio(@RequestBody DomicilioDTO dto) {
        try {
            Domicilio nuevoDomicilio = repository.save(dto);
            return ResponseEntity.status(201).body(nuevoDomicilio);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al agregar el domicilio");
        }
    }

    // 4. Eliminar (Adaptado de "desactivar" a "eliminar")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDomicilio(@PathVariable int id) {
        // Primero verificamos si existe
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id); // Borrado físico
            return ResponseEntity.ok().build(); // 200 OK sin cuerpo
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Domicilio> actualizarDomicilio(@PathVariable int id, @RequestBody PUTDomicilioDTO dto) {
        // Si decidiste no crear PUTDomicilioDTO, cambia el tipo aquí a DomicilioDTO
        Domicilio domicilioActualizado = repository.update(id, dto);

        if (domicilioActualizado != null) {
            return ResponseEntity.ok(domicilioActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}