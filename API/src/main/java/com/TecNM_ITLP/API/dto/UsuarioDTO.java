package com.TecNM_ITLP.API.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.TecNM_ITLP.API.models.Sexo;

public record UsuarioDTO(
    @Schema(description = "Nombre completo", example = "Juan Perez")
    String nombre,
    
    @Schema(description = "Correo electrónico", example = "juan@example.com")
    String email,
    
    @Schema(description = "Teléfono", example = "3521234567")
    String telefono, // Lo recibimos como String en el JSON (más fácil)
    
    @Schema(description = "Género", example = "MASCULINO")
    Sexo sexo,
    
    @Schema(description = "Fecha de nacimiento (YYYY-MM-DD)", example = "1999-12-31")
    String fecha_nacimiento,
    
    @Schema(description = "Contraseña segura", example = "12345")
    String contrasena
) 
{
    
}