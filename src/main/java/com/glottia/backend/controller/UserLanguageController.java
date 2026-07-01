package com.glottia.backend.controller;

import com.glottia.backend.entity.UserLanguage;
import com.glottia.backend.service.UserLanguageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-languages")
@Tag(name = "UsuarioIdiomas", description = "Gestión de idiomas de usuarios")
public class UserLanguageController {

    @Autowired
    private UserLanguageService userLanguageService;

    @GetMapping
    @Operation(summary = "Listar todos los idiomas de usuarios")
    public List<UserLanguage> getAllUserLanguages() {
        return userLanguageService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un idioma de usuario por ID")
    public ResponseEntity<UserLanguage> getUserLanguageById(@PathVariable Integer id) {
        return userLanguageService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Asignar un nuevo idioma a un usuario")
    public UserLanguage createUserLanguage(@RequestBody UserLanguage userLanguage) {
        return userLanguageService.save(userLanguage);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un idioma de usuario")
    public ResponseEntity<UserLanguage> updateUserLanguage(@PathVariable Integer id, @RequestBody UserLanguage userLanguage) {
        return userLanguageService.findById(id)
                .map(existingUserLanguage -> {
                    userLanguage.setIdUsuarioIdioma(id);
                    return ResponseEntity.ok(userLanguageService.save(userLanguage));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{idUsuario}")
    @Operation(summary = "Obtener idiomas por usuario")
    public List<UserLanguage> getIdiomasPorUsuario(@PathVariable Integer idUsuario) {
        return userLanguageService.obtenerIdiomasPorUsuario(idUsuario);
    }

    @GetMapping("/idioma/{idIdioma}")
    @Operation(summary = "Buscar usuarios por idioma")
    public List<UserLanguage> getUsuariosPorIdioma(@PathVariable Integer idIdioma) {
        return userLanguageService.buscarUsuariosPorIdioma(idIdioma);
    }

    @GetMapping("/idioma/{idIdioma}/nivel/{nivel}")
    @Operation(summary = "Buscar usuarios por idioma y nivel")
    public List<UserLanguage> getUsuariosPorIdiomaYNivel(@PathVariable Integer idIdioma, @PathVariable String nivel) {
        return userLanguageService.buscarUsuariosPorIdiomaYNivel(idIdioma, nivel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un idioma de usuario")
    public ResponseEntity<Void> deleteUserLanguage(@PathVariable Integer id) {
        userLanguageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
