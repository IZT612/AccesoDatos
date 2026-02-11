package com.ejemplo.alumnos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ejemplo.alumnos.entity.Alumno;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    
    List<Alumno> findByNombre(String nombre);
    
    List<Alumno> findByNombreContainingIgnoreCase(String texto);

    Optional<Alumno> findByEmail(String email);
}