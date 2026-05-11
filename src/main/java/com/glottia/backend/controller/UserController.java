package com.glottia.backend.controller;

import com.glottia.backend.dto.UserDTO;
import com.glottia.backend.entity.User;
import com.glottia.backend.mapper.EntityMapper;
import com.glottia.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "Gestión de usuarios de Glottia")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EntityMapper mapper;

    @GetMapping
    @Operation(summary = "Listar todos los usuarios")
    public List<UserDTO> getAllUsers() {
        return userService.findAll().stream()
                .map(mapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un usuario por ID")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        return userService.findById(id)
                .map(mapper::toUserDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo usuario")
    public UserDTO createUser(@RequestBody UserDTO userDto) {
        User user = mapper.toUserEntity(userDto);
        return mapper.toUserDTO(userService.save(user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un usuario")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDto) {
        return userService.findById(id)
                .map(existingUser -> {
                    User user = mapper.toUserEntity(userDto);
                    user.setIdUsuario(id);
                    return ResponseEntity.ok(mapper.toUserDTO(userService.save(user)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/correo/{correo}")
    @Operation(summary = "Buscar usuario por correo")
    public ResponseEntity<UserDTO> getUserByCorreo(@PathVariable String correo) {
        return userService.findByCorreo(correo)
                .map(mapper::toUserDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rol/{nombreRol}")
    @Operation(summary = "Listar usuarios por rol")
    public List<UserDTO> getUsuariosPorRol(@PathVariable String nombreRol) {
        return userService.listarUsuariosConRol(nombreRol).stream()
                .map(mapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/ciudad/{ciudad}")
    @Operation(summary = "Buscar usuarios por ciudad")
    public List<UserDTO> getUsuariosPorCiudad(@PathVariable String ciudad) {
        return userService.buscarPorCiudad(ciudad).stream()
                .map(mapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/modalidad/{modalidad}")
    @Operation(summary = "Buscar usuarios por modalidad")
    public List<UserDTO> getUsuariosPorModalidad(@PathVariable String modalidad) {
        return userService.buscarPorModalidad(modalidad).stream()
                .map(mapper::toUserDTO)
                .collect(Collectors.toList());
    }
}
