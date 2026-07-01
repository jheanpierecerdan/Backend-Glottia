package com.glottia.backend.controller;

import com.glottia.backend.dto.LanguageDTO;
import com.glottia.backend.entity.Language;
import com.glottia.backend.service.LanguageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/languages")
@Tag(name = "Idiomas", description = "Gestión de idiomas")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @GetMapping
    @Operation(summary = "Listar todos los idiomas")
    public List<LanguageDTO> getAllLanguages() {
        return languageService.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un idioma por ID")
    public ResponseEntity<LanguageDTO> getLanguageById(@PathVariable Integer id) {
        return languageService.findById(id)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo idioma")
    public LanguageDTO createLanguage(@RequestBody LanguageDTO languageDto) {
        Language language = convertToEntity(languageDto);
        return convertToDto(languageService.save(language));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un idioma")
    public ResponseEntity<LanguageDTO> updateLanguage(@PathVariable Integer id, @RequestBody LanguageDTO languageDto) {
        return languageService.findById(id)
                .map(existingLanguage -> {
                    Language language = convertToEntity(languageDto);
                    language.setIdIdioma(id);
                    return ResponseEntity.ok(convertToDto(languageService.save(language)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un idioma")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Integer id) {
        languageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private LanguageDTO convertToDto(Language language) {
        if (language == null) return null;
        LanguageDTO dto = new LanguageDTO();
        dto.setIdIdioma(language.getIdIdioma());
        dto.setNombre(language.getNombre());
        dto.setCodigoIso(language.getCodigoIso());
        dto.setDescripcion(language.getDescripcion());
        return dto;
    }

    private Language convertToEntity(LanguageDTO dto) {
        if (dto == null) return null;
        Language language = new Language();
        language.setIdIdioma(dto.getIdIdioma());
        language.setNombre(dto.getNombre());
        language.setCodigoIso(dto.getCodigoIso());
        language.setDescripcion(dto.getDescripcion());
        return language;
    }
}

