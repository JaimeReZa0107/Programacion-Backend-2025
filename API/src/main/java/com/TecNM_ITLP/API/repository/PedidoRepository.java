package com.TecNM_ITLP.API.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.TecNM_ITLP.API.models.Pedido;
import com.TecNM_ITLP.API.models.DetalleCarrito;
import com.TecNM_ITLP.API.dto.PedidoDTO;
import com.TecNM_ITLP.API.dto.PUTPedidoDTO;

@Repository
public class PedidoRepository {

    private final JdbcClient jdbcClient;

    public PedidoRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Pedido> findAll() {
        String sql = "SELECT * FROM pedidos";
        return jdbcClient.sql(sql).query(new PedidoRM()).list();
    }

    public Optional<Pedido> findById(int id) {
        String sql = "SELECT * FROM pedidos WHERE id = :id";
        return jdbcClient.sql(sql).param("id", id).query(new PedidoRM()).optional();
    }

    @Transactional 
    public Pedido crearPedidoDesdeCarrito(PedidoDTO dto) {
        
        // 1. Buscamos qué hay en el carrito
        String sqlCarrito = "SELECT * FROM detalles_carrito WHERE usuarios_id = :uid";
        List<DetalleCarrito> carrito = jdbcClient.sql(sqlCarrito)
                .param("uid", dto.usuarios_id()) // <--- Usa el campo que SÍ existe
                .query(new DetalleCarritoRM())
                .list();

        if (carrito.isEmpty()) {
            throw new RuntimeException("El carrito está vacío.");
        }

        // 2. Calculamos nosotros mismos el dinero (Seguridad)
        double importeProductos = carrito.stream().mapToDouble(DetalleCarrito::precio).sum();
        double iva = importeProductos * 0.16;
        
        double total = importeProductos + dto.importe_envio() + iva; 

        // 3. Guardamos
        String numeroPedido = UUID.randomUUID().toString();
        String sqlInsert = """
            INSERT INTO pedidos (fecha, numero, importe_productos, importe_envio, usuarios_id, metodos_pago_id, fecha_hora_pago, importe_iva, total)
            VALUES (NOW(), :num, :prod, :envio, :uid, :pago, NOW(), :iva, :total)
            RETURNING id
        """;

        Integer pedidoId = jdbcClient.sql(sqlInsert)
                .param("num", numeroPedido)
                .param("prod", importeProductos)
                .param("envio", dto.importe_envio())
                .param("uid", dto.usuarios_id())
                .param("pago", dto.metodos_pago_id())
                .param("iva", iva)
                .param("total", total)
                .query((rs, rowNum) -> rs.getInt("id"))
                .single();

        // 4. Movemos historial
        for (DetalleCarrito item : carrito) {
            jdbcClient.sql("INSERT INTO detalles_pedido (cantidad, precio, productos_id, pedidos_id) VALUES (:c, :p, :prod, :ped)")
                    .param("c", item.cantidad())
                    .param("p", item.precio())
                    .param("prod", item.productos_id())
                    .param("ped", pedidoId)
                    .update();
        }

        // 5. Borramos carrito
        jdbcClient.sql("DELETE FROM detalles_carrito WHERE usuarios_id = :uid")
                .param("uid", dto.usuarios_id())
                .update();

        return findById(pedidoId).orElse(null);
    }
    
    public Pedido update(int id, PUTPedidoDTO dto) {
        String sql = "UPDATE pedidos SET importe_envio = :envio WHERE id = :id RETURNING id";
        Optional<Integer> idUpd = jdbcClient.sql(sql)
                .param("id", id)
                .param("envio", dto.importe_envio())
                .query((rs, rowNum) -> rs.getInt("id")).optional();
        return idUpd.isPresent() ? findById(id).orElse(null) : null;
    }
    
    public void deleteById(int id) {
         jdbcClient.sql("DELETE FROM detalles_pedido WHERE pedidos_id = :id").param("id", id).update();
         jdbcClient.sql("DELETE FROM pedidos WHERE id = :id").param("id", id).update();
    }
}