package com.ejemplo.alumnos.security;

import com.ejemplo.alumnos.entity.Alumno;
import com.ejemplo.alumnos.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AlumnoRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Alumno alumno = repo.findByNombre(username)
                .stream()
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return User.withUsername(alumno.getNombre()) 
                .password("{noop}" + alumno.getEmail()) 
                .roles("USER")
                .build();
    }
}