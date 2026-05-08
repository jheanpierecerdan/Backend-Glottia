package com.glottia.backend.controller;

import com.glottia.backend.entity.Event;
import com.glottia.backend.service.EventService;
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

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un evento")
    public ResponseEntity<Event> updateEvent(@PathVariable Integer id, @RequestBody Event event) {
        return eventService.findById(id)
                .map(existingEvent -> {
                    event.setIdEvento(id);
                    return ResponseEntity.ok(eventService.save(event));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/activos")
    @Operation(summary = "Listar eventos activos")
    public List<Event> getEventosActivos() {
        return eventService.listarEventosActivos();
    }

    @GetMapping("/idioma/{idIdioma}")
    @Operation(summary = "Buscar eventos por ID de idioma")
    public List<Event> getEventosPorIdioma(@PathVariable Integer idIdioma) {
        return eventService.buscarEventosPorIdioma(idIdioma);
    }

    @GetMapping("/modalidad/{modalidad}")
    @Operation(summary = "Buscar eventos por modalidad")
    public List<Event> getEventosPorModalidad(@PathVariable String modalidad) {
        return eventService.buscarEventosPorModalidad(modalidad);
    }

    @GetMapping("/futuros")
    @Operation(summary = "Listar eventos futuros")
    public List<Event> getEventosFuturos() {
        return eventService.listarEventosFuturos();
    }

    @GetMapping("/creador/{idUsuario}")
    @Operation(summary = "Obtener eventos creados por un usuario")
    public List<Event> getEventosPorCreador(@PathVariable Integer idUsuario) {
        return eventService.obtenerEventosPorUsuarioCreador(idUsuario);
    }
}

