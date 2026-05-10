package com.glottia.backend.controller;

import com.glottia.backend.entity.Interest;
import com.glottia.backend.service.InterestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interests")
@Tag(name = "Intereses", description = "Gestión de intereses")
public class InterestController {

    @Autowired
    private InterestService interestService;

    @GetMapping
    @Operation(summary = "Listar todos los intereses")
    public List<Interest> getAllInterests() {
        return interestService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un interes por ID")
    public ResponseEntity<Interest> getInterestById(@PathVariable Integer id) {
        return interestService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo interes")
    public Interest createInterest(@RequestBody Interest interest) {
        return interestService.save(interest);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un interes")
    public ResponseEntity<Interest> updateInterest(@PathVariable Integer id, @RequestBody Interest interest) {
        return interestService.findById(id)
                .map(existingInterest -> {
                    interest.setIdInteres(id);
                    return ResponseEntity.ok(interestService.save(interest));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un interes")
    public ResponseEntity<Void> deleteInterest(@PathVariable Integer id) {
        interestService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
