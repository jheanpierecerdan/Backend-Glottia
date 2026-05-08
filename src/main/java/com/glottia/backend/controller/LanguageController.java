package com.glottia.backend.controller;

import com.glottia.backend.entity.Language;
import com.glottia.backend.service.LanguageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
@Tag(name = "Idiomas", description = "Gestión de idiomas")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @GetMapping
    @Operation(summary = "Listar todos los idiomas")
    public List<Language> getAllLanguages() {
        return languageService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un idioma por ID")
    public ResponseEntity<Language> getLanguageById(@PathVariable Integer id) {
        return languageService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo idioma")
    public Language createLanguage(@RequestBody Language language) {
        return languageService.save(language);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un idioma")
    public ResponseEntity<Language> updateLanguage(@PathVariable Integer id, @RequestBody Language language) {
        return languageService.findById(id)
                .map(existingLanguage -> {
                    language.setIdIdioma(id);
                    return ResponseEntity.ok(languageService.save(language));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un idioma")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Integer id) {
        languageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

