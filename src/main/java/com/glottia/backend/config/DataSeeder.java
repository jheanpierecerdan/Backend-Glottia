package com.glottia.backend.config;

import com.glottia.backend.entity.Role;
import com.glottia.backend.entity.User;
import com.glottia.backend.repository.RoleRepository;
import com.glottia.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataSeeder(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear Roles si no existen
        Role adminRole = createRoleIfNotFound("ADMINISTRADOR", "Rol de administrador del sistema");
        Role organizadorRole = createRoleIfNotFound("ORGANIZADOR", "Rol de organizador de eventos");
        Role docenteRole = createRoleIfNotFound("DOCENTE", "Rol de docente que gestiona reuniones");
        Role estudianteRole = createRoleIfNotFound("ESTUDIANTE", "Rol de estudiante que puede participar y reservar");

        // Crear Usuarios para cada Rol si no existen
        createUserIfNotFound("admin@glottia.com", "admin123", "Admin", "User", adminRole);
        createUserIfNotFound("organizador@glottia.com", "org123", "Organizador", "User", organizadorRole);
        createUserIfNotFound("docente@glottia.com", "docente123", "Docente", "User", docenteRole);
        createUserIfNotFound("estudiante@glottia.com", "estudiante123", "Estudiante", "User", estudianteRole);
    }

    private Role createRoleIfNotFound(String nombre, String descripcion) {
        Optional<Role> roleOptional = roleRepository.findByNombre(nombre);
        if (roleOptional.isEmpty()) {
            Role role = new Role();
            role.setNombre(nombre);
            role.setDescripcion(descripcion);
            return roleRepository.save(role);
        }
        return roleOptional.get();
    }

    private void createUserIfNotFound(String correo, String contrasenaPlana, String nombre, String apellido, Role rol) {
        Optional<User> userOptional = userRepository.buscarPorCorreo(correo);
        if (userOptional.isEmpty()) {
            User user = new User();
            user.setCorreo(correo);
            user.setContrasena(passwordEncoder.encode(contrasenaPlana));
            user.setNombre(nombre);
            user.setApellido(apellido);
            user.setRol(rol);
            user.setEstado(true);
            userRepository.save(user);
        }
    }
}
