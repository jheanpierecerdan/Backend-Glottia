package com.glottia.backend.controller;

import com.glottia.backend.dto.UserDTO;
import com.glottia.backend.entity.Role;
import com.glottia.backend.entity.User;
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

    @GetMapping
    @Operation(summary = "Listar todos los usuarios")
    public List<UserDTO> getAllUsers() {
        return userService.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un usuario por ID")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        return userService.findById(id)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo usuario")
    public UserDTO createUser(@RequestBody UserDTO userDto) {
        User user = convertToEntity(userDto);
        return convertToDto(userService.save(user));
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
                    User user = convertToEntity(userDto);
                    user.setIdUsuario(id);
                    return ResponseEntity.ok(convertToDto(userService.save(user)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/correo/{correo}")
    @Operation(summary = "Buscar usuario por correo")
    public ResponseEntity<UserDTO> getUserByCorreo(@PathVariable String correo) {
        return userService.findByCorreo(correo)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rol/{nombreRol}")
    @Operation(summary = "Listar usuarios por rol")
    public List<UserDTO> getUsuariosPorRol(@PathVariable String nombreRol) {
        return userService.listarUsuariosConRol(nombreRol).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/ciudad/{ciudad}")
    @Operation(summary = "Buscar usuarios por ciudad")
    public List<UserDTO> getUsuariosPorCiudad(@PathVariable String ciudad) {
        return userService.buscarPorCiudad(ciudad).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/modalidad/{modalidad}")
    @Operation(summary = "Buscar usuarios por modalidad")
    public List<UserDTO> getUsuariosPorModalidad(@PathVariable String modalidad) {
        return userService.buscarPorModalidad(modalidad).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private UserDTO convertToDto(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setIdUsuario(user.getIdUsuario());
        dto.setNombre(user.getNombre());
        dto.setApellido(user.getApellido());
        dto.setCorreo(user.getCorreo());
        dto.setContrasena(user.getContrasena());
        dto.setCiudad(user.getCiudad());
        dto.setBiografia(user.getBiografia());
        dto.setModalidad(user.getModalidad());
        dto.setFechaRegistro(user.getFechaRegistro());
        dto.setEstado(user.getEstado());
        if (user.getRol() != null) {
            dto.setIdRol(user.getRol().getIdRol());
        }
        return dto;
    }

    private User convertToEntity(UserDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setIdUsuario(dto.getIdUsuario());
        user.setNombre(dto.getNombre());
        user.setApellido(dto.getApellido());
        user.setCorreo(dto.getCorreo());
        user.setContrasena(dto.getContrasena());
        user.setCiudad(dto.getCiudad());
        user.setBiografia(dto.getBiografia());
        user.setModalidad(dto.getModalidad());
        user.setFechaRegistro(dto.getFechaRegistro());
        user.setEstado(dto.getEstado());
        if (dto.getIdRol() != null) {
            Role role = new Role();
            role.setIdRol(dto.getIdRol());
            user.setRol(role);
        }
        return user;
    }
}
