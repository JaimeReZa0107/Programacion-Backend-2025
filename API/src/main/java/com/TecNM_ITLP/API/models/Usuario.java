package com.TecNM_ITLP.API.models;

// No necesitas importar nada extra porque estás usando tipos básicos y tu Enum
public record Usuario(
    int id, 
    String nombre, 
    String email, 
    char[] telefono, 
    Sexo sexo, 
    String fecha_nacimiento, 
    String contrasena, 
    String fecha_registro
) {}