package com.TecNM_ITLP.API.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.TecNM_ITLP.API.models.Domicilio;
import com.TecNM_ITLP.API.repository.DomicilioRepository;
import com.TecNM_ITLP.API.dto.DomicilioDTO;
import com.TecNM_ITLP.API.dto.PUTDomicilioDTO;

@RestController
@RequestMapping("/domicilios")
@Tag(name = "Domicilios", description = "Gestión de los domicilios de los usuarios") 
public class DomicilioController {

    @Autowired
    private DomicilioRepository repository;

    @GetMapping()
    @Operation(summary = "Listar todos los domicilios", description = "Devuelve el listado completo de direcciones registradas en el sistema")
    public ResponseEntity<List<Domicilio>> obtenerDomicilios() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar domicilio por ID", description = "Obtiene los detalles de una dirección específica")
    public ResponseEntity<Domicilio> obtenerDomicilioPorId(@PathVariable int id) {
        return repository.findById(id)
                .map(domicilio -> ResponseEntity.ok(domicilio))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Registrar nuevo domicilio", description = "Guarda una nueva dirección asociada a un usuario existente")
    public ResponseEntity<?> insertarDomicilio(@RequestBody DomicilioDTO dto) {
        try {
            Domicilio nuevoDomicilio = repository.save(dto);
            return ResponseEntity.status(201).body(nuevoDomicilio);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al agregar el domicilio: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar domicilio", description = "Borra permanentemente una dirección de la base de datos")
    public ResponseEntity<Void> eliminarDomicilio(@PathVariable int id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar domicilio", description = "Modifica los datos de una dirección existente (calle, número, etc.)")
    public ResponseEntity<Domicilio> actualizarDomicilio(@PathVariable int id, @RequestBody PUTDomicilioDTO dto) {
        Domicilio domicilioActualizado = repository.update(id, dto);

        if (domicilioActualizado != null) {
            return ResponseEntity.ok(domicilioActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}