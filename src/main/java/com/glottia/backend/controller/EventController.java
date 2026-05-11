package com.glottia.backend.controller;

import com.glottia.backend.dto.EventDTO;
import com.glottia.backend.entity.Event;
import com.glottia.backend.mapper.EntityMapper;
import com.glottia.backend.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Eventos", description = "Gestión de eventos e intercambios de idiomas")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EntityMapper mapper;

    @GetMapping
    @Operation(summary = "Listar todos los eventos")
    public List<EventDTO> getAllEvents() {
        return eventService.findAll().stream()
                .map(mapper::toEventDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un evento por ID")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Integer id) {
        return eventService.findById(id)
                .map(mapper::toEventDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo evento")
    public EventDTO createEvent(@RequestBody EventDTO eventDto) {
        Event event = mapper.toEventEntity(eventDto);
        return mapper.toEventDTO(eventService.save(event));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un evento")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer id) {
        eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un evento")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Integer id, @RequestBody EventDTO eventDto) {
        return eventService.findById(id)
                .map(existingEvent -> {
                    Event event = mapper.toEventEntity(eventDto);
                    event.setIdEvento(id);
                    return ResponseEntity.ok(mapper.toEventDTO(eventService.save(event)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/activos")
    @Operation(summary = "Listar eventos activos")
    public List<EventDTO> getEventosActivos() {
        return eventService.listarEventosActivos().stream()
                .map(mapper::toEventDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/idioma/{idIdioma}")
    @Operation(summary = "Buscar eventos por ID de idioma")
    public List<EventDTO> getEventosPorIdioma(@PathVariable Integer idIdioma) {
        return eventService.buscarEventosPorIdioma(idIdioma).stream()
                .map(mapper::toEventDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/modalidad/{modalidad}")
    @Operation(summary = "Buscar eventos por modalidad")
    public List<EventDTO> getEventosPorModalidad(@PathVariable String modalidad) {
        return eventService.buscarEventosPorModalidad(modalidad).stream()
                .map(mapper::toEventDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/futuros")
    @Operation(summary = "Listar eventos futuros")
    public List<EventDTO> getEventosFuturos() {
        return eventService.listarEventosFuturos().stream()
                .map(mapper::toEventDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/creador/{idUsuario}")
    @Operation(summary = "Obtener eventos creados por un usuario")
    public List<EventDTO> getEventosPorCreador(@PathVariable Integer idUsuario) {
        return eventService.obtenerEventosPorUsuarioCreador(idUsuario).stream()
                .map(mapper::toEventDTO)
                .collect(Collectors.toList());
    }
}
