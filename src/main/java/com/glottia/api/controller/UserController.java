package com.glottia.api.controller;

import com.glottia.api.model.User;
import com.glottia.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "Gestión de usuarios de Glottia")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Listar todos los usuarios")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un usuario por ID")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo usuario")
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un usuario")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        return userService.findById(id)
                .map(existingUser -> {
                    user.setIdUsuario(id);
                    return ResponseEntity.ok(userService.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/correo/{correo}")
    @Operation(summary = "Buscar usuario por correo")
    public ResponseEntity<User> getUserByCorreo(@PathVariable String correo) {
        return userService.findByCorreo(correo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rol/{nombreRol}")
    @Operation(summary = "Listar usuarios por rol")
    public List<User> getUsuariosPorRol(@PathVariable String nombreRol) {
        return userService.listarUsuariosConRol(nombreRol);
    }

    @GetMapping("/ciudad/{ciudad}")
    @Operation(summary = "Buscar usuarios por ciudad")
    public List<User> getUsuariosPorCiudad(@PathVariable String ciudad) {
        return userService.buscarPorCiudad(ciudad);
    }

    @GetMapping("/modalidad/{modalidad}")
    @Operation(summary = "Buscar usuarios por modalidad")
    public List<User> getUsuariosPorModalidad(@PathVariable String modalidad) {
        return userService.buscarPorModalidad(modalidad);
    }
}
