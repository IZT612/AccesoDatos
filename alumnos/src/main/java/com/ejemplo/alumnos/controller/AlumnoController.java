package com.ejemplo.alumnos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ejemplo.alumnos.entity.Alumno;
import com.ejemplo.alumnos.repository.AlumnoRepository;
import com.ejemplo.alumnos.security.dtos.AlumnoDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {
	
	@PostMapping("/validar")
    public String registrarAlumno(@Valid @RequestBody AlumnoDTO alumno) {
        return "Alumno validado correctamente: " + alumno.getEmail();
    }
	
    @GetMapping("/")
    public String home() {
        return "App funcionando";
    }

    @GetMapping("/hola")
    public String hola() {
        return "¡Hola Mundo desde Spring Boot!";
    }
    
    @GetMapping("/saludo/{nombre}")
    public String saludo(@PathVariable String nombre) {
        return "¡Hola " + nombre + "!";
    }

    @Autowired
    private AlumnoRepository repo;

    @GetMapping
    public List<Alumno> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> obtenerPorId(@PathVariable Long id) {
        Optional<Alumno> alumno = repo.findById(id);
        
        if (alumno.isPresent()) {
            return ResponseEntity.ok(alumno.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Alumno> crear(@RequestBody Alumno alumno) {
        Alumno alumnoGuardado = repo.save(alumno);
        return ResponseEntity.ok(alumnoGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alumno> actualizar(@PathVariable Long id, @RequestBody Alumno alumnoDetalles) {
        Optional<Alumno> alumnoOptional = repo.findById(id);

        if (alumnoOptional.isPresent()) {
            Alumno alumnoExistente = alumnoOptional.get();
            
            alumnoExistente.setNombre(alumnoDetalles.getNombre());
            alumnoExistente.setEmail(alumnoDetalles.getEmail());

            Alumno alumnoActualizado = repo.save(alumnoExistente);
            return ResponseEntity.ok(alumnoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/buscar")
    public List<Alumno> buscarPorNombre(@RequestParam String nombre) {
        return repo.findByNombreContainingIgnoreCase(nombre);
    }
}