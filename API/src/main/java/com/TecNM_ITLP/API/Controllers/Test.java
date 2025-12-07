package com.TecNM_ITLP.API.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TecNM_ITLP.API.models.Producto;

@RequestMapping("/test")
@RestController
public class Test {

    //Endpoint 1 "Hello"
    @GetMapping("/hello")
    public String helloworld(){
        return "Hola API Rest";
    }

    //Endpoint 2 "producto"
    @GetMapping("/producto")
    public Producto getProducto() {
        Producto p = new Producto();
        p.nombre = "Coca Cola";
        p.precio = 18.5;
        p.codigoBarras = "7501055312306";
        return p;
    }

    //Endpoint 3 "Productos"
    @GetMapping("/productos")
    public Producto[] getProductos() {
        Producto p1 = new Producto();
        p1.nombre = "Coca Cola";
        p1.precio = 18.5;
        p1.codigoBarras = "7501055312306";

        Producto p2 = new Producto();
        p2.nombre = "Pepsi";
        p2.precio = 17.5;
        p2.codigoBarras = "7501055312307";

        Producto p3 = new Producto();
        p3.nombre = "Fanta";
        p3.precio = 16.5;
        p3.codigoBarras = "7501055312308";

        return new Producto[]{p1, p2, p3};
    }

    //Endpoint 4 "Productos por id"
@GetMapping("/Productos/{id}")
// Cambia <Producto> por <?> (comodín)
public ResponseEntity<?> buscarPorId(@PathVariable int id) {
    if (id < 0 || id > 2) {
        // Esto ahora es válido porque ResponseEntity<Void> encaja en ResponseEntity<?>
        return ResponseEntity.notFound().build();
    }
    
    Producto[] productos = new Producto[3];
    Producto p1 = new Producto();
    p1.nombre = "Coca Cola";
    p1.precio = 18.5;
    p1.codigoBarras = "7501055312306";
    productos[0] = p1;

    Producto p2 = new Producto();
    p2.nombre = "Pepsi";
    p2.precio = 17.5;
    p2.codigoBarras = "7501055312307";
    productos[1] = p2;

    Producto p3 = new Producto();
    p3.nombre = "Fanta";
    p3.precio = 16.5;
    p3.codigoBarras = "7501055312308";
    productos[2] = p3;

    Producto resultado = productos[id];
    // Esto también es válido porque ResponseEntity<Producto> encaja en ResponseEntity<?>
    return ResponseEntity.ok(resultado);
    }

}