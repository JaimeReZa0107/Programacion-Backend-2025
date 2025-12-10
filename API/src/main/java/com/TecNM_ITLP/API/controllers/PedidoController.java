package com.TecNM_ITLP.API.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.TecNM_ITLP.API.models.Pedido;
import com.TecNM_ITLP.API.repository.PedidoRepository;
import com.TecNM_ITLP.API.dto.PedidoDTO;
import com.TecNM_ITLP.API.dto.PUTPedidoDTO;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Endpoints para administrar las órdenes de compra")
public class PedidoController {

    @Autowired
    private PedidoRepository repository;

    @GetMapping
    @Operation(summary = "Listar todos los pedidos", description = "Obtiene el historial completo de pedidos registrados")
    public ResponseEntity<List<Pedido>> obtenerPedidos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Obtiene el detalle de una orden específica")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo pedido", description = "Genera una nueva orden de compra. El número de pedido (UUID) y la fecha se generan automáticamente.")
    public ResponseEntity<?> insertarPedido(@RequestBody PedidoDTO dto) {
        try {
            Pedido nuevo = repository.save(dto);
            return ResponseEntity.status(201).body(nuevo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al crear el pedido: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar estado de pago", description = "Permite registrar la fecha de pago y actualizar el método de pago")
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable int id, @RequestBody PUTPedidoDTO dto) {
        Pedido actualizado = repository.update(id, dto);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pedido", description = "Borra permanentemente un pedido del sistema")
    public ResponseEntity<Void> eliminarPedido(@PathVariable int id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}