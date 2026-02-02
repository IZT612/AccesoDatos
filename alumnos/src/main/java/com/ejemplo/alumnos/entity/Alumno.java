package com.ejemplo.alumnos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Alumno {
    @Id 
    @GeneratedValue
    private Long id;
    private String nombre;
    private String email;
    
    // Getters y setters
}

