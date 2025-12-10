package com.TecNM_ITLP.API.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.TecNM_ITLP.API.models.Categoria;
import com.TecNM_ITLP.API.repository.CategoriaRepository;
import com.TecNM_ITLP.API.dto.CategoriaDTO;
import com.TecNM_ITLP.API.dto.PUTCategoriaDTO;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorías", description = "Administración de las categorías de productos") 
public class CategoriaController {

    @Autowired
    private CategoriaRepository repository;

    // 1. Obtener todas
    @GetMapping()
    @Operation(summary = "Listar todas las categorías", description = "Devuelve un listado completo de las categorías disponibles en el sistema")
    public ResponseEntity<List<Categoria>> obtenerCategorias() {
        return ResponseEntity.ok(repository.findAll());
    }

    // 2. Obtener por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoría por ID", description = "Obtiene los detalles de una categoría específica")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable int id) {
        return repository.findById(id)
                .map(categoria -> ResponseEntity.ok(categoria))
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Insertar
    @PostMapping
    @Operation(summary = "Crear nueva categoría", description = "Registra una nueva categoría en la base de datos")
    public ResponseEntity<?> insertarCategoria(@RequestBody CategoriaDTO dto) {
        try {
            Categoria nuevaCategoria = repository.save(dto);
            return ResponseEntity.status(201).body(nuevaCategoria);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al crear la categoría: " + e.getMessage());
        }
    }

    // 4. Actualizar
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categoría", description = "Modifica el nombre de una categoría existente")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable int id, @RequestBody PUTCategoriaDTO dto) {
        Categoria categoriaActualizada = repository.update(id, dto);
        
        if (categoriaActualizada != null) {
            return ResponseEntity.ok(categoriaActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. Eliminar
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar categoría", description = "Elimina permanentemente una categoría (Cuidado: verificar productos asociados antes)")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable int id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}