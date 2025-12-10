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
@Tag(name = "Pedidos y Checkout", description = "Procesamiento de órdenes de compra")
public class PedidoController {

    @Autowired
    private PedidoRepository repository;

    @GetMapping
    @Operation(summary = "Historial de pedidos", description = "Lista todas las compras realizadas")
    public ResponseEntity<List<Pedido>> obtenerPedidos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ver pedido por ID", description = "Detalles de una orden específica (encabezado)")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "PROCESAR COMPRA (Checkout)", description = "Convierte el carrito del usuario en un pedido oficial, calcula totales y vacía el carrito.")
    public ResponseEntity<?> crearPedido(@RequestBody PedidoDTO dto) {
        try {
            Pedido nuevoPedido = repository.crearPedidoDesdeCarrito(dto);
            return ResponseEntity.status(201).body(nuevoPedido);
        } catch (RuntimeException e) {
            // Errores de negocio (ej: carrito vacío)
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error en el checkout: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar importes", description = "Modifica montos del pedido (Solo Admin)")
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable int id, @RequestBody PUTPedidoDTO dto) {
        Pedido actualizado = repository.update(id, dto);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pedido", description = "Borra un pedido y sus detalles asociados")
    public ResponseEntity<Void> eliminarPedido(@PathVariable int id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}