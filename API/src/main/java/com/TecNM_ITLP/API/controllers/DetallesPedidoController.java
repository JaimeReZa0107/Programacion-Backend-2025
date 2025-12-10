package com.TecNM_ITLP.API.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.TecNM_ITLP.API.models.DetallesPedido;
import com.TecNM_ITLP.API.repository.DetallesPedidoRepository;
import com.TecNM_ITLP.API.dto.DetallesPedidoDTO;
import com.TecNM_ITLP.API.dto.PUTDetallesPedidoDTO;

@RestController
@RequestMapping("/detalles_pedido")
@Tag(name = "Detalles de Pedido", description = "Gestión de los productos contenidos en cada pedido (Historial)")
public class DetallesPedidoController {

    @Autowired
    private DetallesPedidoRepository repository;

    @GetMapping
    @Operation(summary = "Listar todos los detalles", description = "Muestra todos los registros de productos vendidos en todos los pedidos")
    public ResponseEntity<List<DetallesPedido>> obtenerTodos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar detalle por ID", description = "Obtiene una línea específica de un pedido")
    public ResponseEntity<DetallesPedido> obtenerPorId(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pedido/{pedidoId}")
    @Operation(summary = "Ver contenido de un pedido", description = "Obtiene la lista de productos comprados en un pedido específico")
    public ResponseEntity<List<DetallesPedido>> obtenerPorPedido(@PathVariable int pedidoId) {
        return ResponseEntity.ok(repository.findByPedidoId(pedidoId));
    }

    @PostMapping
    @Operation(summary = "Crear detalle manualmente", description = "Agrega un producto a un pedido (Nota: Usualmente esto lo hace el Checkout automáticamente)")
    public ResponseEntity<?> insertarDetalle(@RequestBody DetallesPedidoDTO dto) {
        try {
            DetallesPedido nuevo = repository.save(dto);
            return ResponseEntity.status(201).body(nuevo);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar detalle", description = "Modifica cantidad o precio de un registro de venta")
    public ResponseEntity<DetallesPedido> actualizarDetalle(@PathVariable int id, @RequestBody PUTDetallesPedidoDTO dto) {
        DetallesPedido actualizado = repository.update(id, dto);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar detalle", description = "Borra un producto de un pedido")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable int id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}