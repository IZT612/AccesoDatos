package com.ejemplo.alumnos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ejemplo.alumnos.entity.Alumno;
import com.ejemplo.alumnos.repository.AlumnoRepository;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
	 info = @Info(
	 title = "Mi API",
	 version = "1.0",
	 description = "API REST")
 )



@SpringBootApplication
public class AlumnosApplication {
	
	@Bean
	CommandLineRunner init(AlumnoRepository repo) {
	    return args -> {
	        repo.save(new Alumno("admin", "admin@admin.com"));
	    };
	}

	public static void main(String[] args) {
		SpringApplication.run(AlumnosApplication.class, args);
	}

}
