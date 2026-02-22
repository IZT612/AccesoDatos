package com.ejemplo.alumnos.security.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AlumnoDTO {

	@Schema(description = "Correo electrónico del alumno", example = "estudiante@ejemplo.com")
    @NotNull(message = "El email no puede ser nulo")
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Formato de email inválido")
    private String email;

    // Getters y Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}