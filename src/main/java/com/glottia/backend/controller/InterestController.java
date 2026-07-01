package com.glottia.backend.controller;

import com.glottia.backend.dto.InterestDTO;
import com.glottia.backend.entity.Interest;
import com.glottia.backend.service.InterestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/interests")
@Tag(name = "Intereses", description = "Gestión de intereses")
public class InterestController {

    @Autowired
    private InterestService interestService;

    @GetMapping
    @Operation(summary = "Listar todos los intereses")
    public List<InterestDTO> getAllInterests() {
        return interestService.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un interes por ID")
    public ResponseEntity<InterestDTO> getInterestById(@PathVariable Integer id) {
        return interestService.findById(id)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo interes")
    public InterestDTO createInterest(@RequestBody InterestDTO interestDto) {
        Interest interest = convertToEntity(interestDto);
        return convertToDto(interestService.save(interest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un interes")
    public ResponseEntity<InterestDTO> updateInterest(@PathVariable Integer id, @RequestBody InterestDTO interestDto) {
        return interestService.findById(id)
                .map(existingInterest -> {
                    Interest interest = convertToEntity(interestDto);
                    interest.setIdInteres(id);
                    return ResponseEntity.ok(convertToDto(interestService.save(interest)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un interes")
    public ResponseEntity<Void> deleteInterest(@PathVariable Integer id) {
        interestService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private InterestDTO convertToDto(Interest interest) {
        if (interest == null) return null;
        InterestDTO dto = new InterestDTO();
        dto.setIdInteres(interest.getIdInteres());
        dto.setNombre(interest.getNombre());
        dto.setDescripcion(interest.getDescripcion());
        return dto;
    }

    private Interest convertToEntity(InterestDTO dto) {
        if (dto == null) return null;
        Interest interest = new Interest();
        interest.setIdInteres(dto.getIdInteres());
        interest.setNombre(dto.getNombre());
        interest.setDescripcion(dto.getDescripcion());
        return interest;
    }
}
