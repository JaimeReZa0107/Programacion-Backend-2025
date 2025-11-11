package com.TecNM_ITLP.API.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TecNM_ITLP.API.models.Producto;

@RequestMapping("/test")
@RestController
public class Test {

    @GetMapping("/hello")
    public String helloworld(){
        return "Hola API Rest";
    }

    @GetMapping("/producto")
    public Producto getProducto() {
        Producto p = new Producto();
        p.nombre = "Coca Cola";
        p.precio = 18.5;
        p.codigoBarras = "7501055312306";
        return p;
    }

    
}