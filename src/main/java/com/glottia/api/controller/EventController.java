package com.glottia.api.controller;

import com.glottia.api.model.Event;
import com.glottia.api.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Eventos", description = "Gestión de eventos e intercambios de idiomas")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    @Operation(summary = "Listar todos los eventos")
    public List<Event> getAllEvents() {
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un evento por ID")
    public ResponseEntity<Event> getEventById(@PathVariable Integer id) {
        return eventService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo evento")
    public Event createEvent(@RequestBody Event event) {
        return eventService.save(event);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un evento")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer id) {
        eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
