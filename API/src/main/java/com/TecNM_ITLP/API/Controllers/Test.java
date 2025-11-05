package com.TecNM_ITLP.API.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class Test {

    @GetMapping("/hello")
    public String helloworld(){
        return "Hola API Rest";
    }
    
}