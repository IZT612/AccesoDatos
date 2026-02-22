package com.ejemplo.alumnos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplo.alumnos.entity.Alumno;
import com.ejemplo.alumnos.repository.AlumnoRepository;
import com.ejemplo.alumnos.security.dtos.AlumnoDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/alumnos")
@Tag(name = "Alumnos", description = "Gestión del CRUD de alumnos y validaciones")
public class AlumnoController {
	
	@Operation(summary = "Validar DTO de Alumno", description = "Verifica si el email cumple con los requisitos de seguridad")
    @ApiResponse(responseCode = "200", description = "Validación exitosa")
    @ApiResponse(responseCode = "400", description = "Email inválido o vacío")
	@PostMapping("/validar")
    public String registrarAlumno(@Valid @RequestBody AlumnoDTO alumno) {
        return "Alumno validado correctamente: " + alumno.getEmail();
    }
	
	@Operation(summary = "Home para comprobar que la app funciona")
    @GetMapping("/")
    public String home() {
        return "App funcionando";
    }

	@Operation(summary = "Ruta de prueba")
    @GetMapping("/hola")
    public String hola() {
        return "¡Hola Mundo desde Spring Boot!";
    }
    
	@Operation(summary = "Ruta de prueba para obtener datos mediante la URL")
    @GetMapping("/saludo/{nombre}")
    public String saludo(@PathVariable String nombre) {
        return "¡Hola " + nombre + "!";
    }

    @Autowired
    private AlumnoRepository repo;

	@Operation(summary = "Listar todos los alumnos")
    @GetMapping
    public List<Alumno> listar() {
        return repo.findAll();
    }

	@Operation(summary = "Obtener alumno por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Alumno> obtenerPorId(@PathVariable Long id) {
        Optional<Alumno> alumno = repo.findById(id);
        
        if (alumno.isPresent()) {
            return ResponseEntity.ok(alumno.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

	@Operation(summary = "Registrar nuevo alumno en BDD")
    @PostMapping
    public ResponseEntity<Alumno> crear(@RequestBody Alumno alumno) {
        Alumno alumnoGuardado = repo.save(alumno);
        return ResponseEntity.ok(alumnoGuardado);
    }

	@Operation(summary = "Actualiza un alumno en la BDD")
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

	@Operation(summary = "Elimina un alumno en la BDD")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
	@Operation(summary = "Busca un alumno de la BDD con el nombre")
    @GetMapping("/buscar")
    public List<Alumno> buscarPorNombre(@RequestParam String nombre) {
        return repo.findByNombreContainingIgnoreCase(nombre);
    }
}