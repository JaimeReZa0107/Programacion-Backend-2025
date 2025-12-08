package com.TecNM_ITLP.API.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; 

import com.TecNM_ITLP.API.models.Categoria;
import com.TecNM_ITLP.API.repository.CategoriaRepository;
import com.TecNM_ITLP.API.dto.CategoriaDTO;
import com.TecNM_ITLP.API.dto.PUTCategoriaDTO;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository repository;

    // 1. Obtener todas
    @GetMapping()
    public ResponseEntity<List<Categoria>> obtenerCategorias() {
        List<Categoria> resultado = repository.findAll();
        return ResponseEntity.ok(resultado);
    }

    // 2. Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable int id) {
        return repository.findById(id)
                .map(categoria -> ResponseEntity.ok(categoria))
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Insertar 
    @PostMapping
    public ResponseEntity<Categoria> insertarCategoria(@RequestBody CategoriaDTO dto) {
        try {
            Categoria nuevaCategoria = repository.save(dto);
            return ResponseEntity.status(201).body(nuevaCategoria);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 4. Actualizar
    @PutMapping("/{id}")
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
    public ResponseEntity<Void> eliminarCategoria(@PathVariable int id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}