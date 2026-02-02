package com.ejemplo.alumnos.repository;

import java.util.List;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.ejemplo.alumnos.entity.Alumno;

@Repository
public interface AlumnoRepository 
    extends JpaRepository<Alumno, Long> {
    // Métodos CRUD heredados
    // Query methods personalizados
    List<Alumno> findByNombre(String nombre);
}
