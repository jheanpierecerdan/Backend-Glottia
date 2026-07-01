package com.glottia.backend.controller;

import com.glottia.backend.entity.UserInterest;
import com.glottia.backend.service.UserInterestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-interests")
@Tag(name = "UsuarioIntereses", description = "Gestión de intereses de usuarios")
public class UserInterestController {

    @Autowired
    private UserInterestService userInterestService;

    @GetMapping
    @Operation(summary = "Listar todos los intereses de usuarios")
    public List<UserInterest> getAllUserInterests() {
        return userInterestService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un interes de usuario por ID")
    public ResponseEntity<UserInterest> getUserInterestById(@PathVariable Integer id) {
        return userInterestService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{usuarioId}")
    @Operation(summary = "Listar intereses por ID de usuario")
    public List<UserInterest> getUserInterestsByUserId(@PathVariable Integer usuarioId) {
        return userInterestService.findByUsuarioId(usuarioId);
    }

    @PostMapping
    @Operation(summary = "Asignar un nuevo interes a un usuario")
    public UserInterest createUserInterest(@RequestBody UserInterest userInterest) {
        return userInterestService.save(userInterest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un interes de usuario")
    public ResponseEntity<Void> deleteUserInterest(@PathVariable Integer id) {
        userInterestService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un interes de usuario")
    public ResponseEntity<UserInterest> updateUserInterest(@PathVariable Integer id, @RequestBody UserInterest userInterest) {
        return userInterestService.findById(id)
                .map(existingUserInterest -> {
                    userInterest.setIdUsuarioInteres(id);
                    return ResponseEntity.ok(userInterestService.save(userInterest));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/interes/{idInteres}")
    @Operation(summary = "Buscar usuarios por ID de interes")
    public List<UserInterest> getUsuariosPorInteres(@PathVariable Integer idInteres) {
        return userInterestService.buscarUsuariosPorInteres(idInteres);
    }
}
