package com.glottia.backend.controller;

import com.glottia.backend.dto.EventDTO;
import com.glottia.backend.entity.Event;
import com.glottia.backend.entity.Language;
import com.glottia.backend.entity.User;
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

    @GetMapping
    @Operation(summary = "Listar todos los eventos")
    public List<EventDTO> getAllEvents() {
        return eventService.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un evento por ID")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Integer id) {
        return eventService.findById(id)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo evento")
    public EventDTO createEvent(@RequestBody EventDTO eventDto) {
        Event event = convertToEntity(eventDto);
        return convertToDto(eventService.save(event));
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
                    Event event = convertToEntity(eventDto);
                    event.setIdEvento(id);
                    return ResponseEntity.ok(convertToDto(eventService.save(event)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/activos")
    @Operation(summary = "Listar eventos activos")
    public List<EventDTO> getEventosActivos() {
        return eventService.listarEventosActivos().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/idioma/{idIdioma}")
    @Operation(summary = "Buscar eventos por ID de idioma")
    public List<EventDTO> getEventosPorIdioma(@PathVariable Integer idIdioma) {
        return eventService.buscarEventosPorIdioma(idIdioma).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/modalidad/{modalidad}")
    @Operation(summary = "Buscar eventos por modalidad")
    public List<EventDTO> getEventosPorModalidad(@PathVariable String modalidad) {
        return eventService.buscarEventosPorModalidad(modalidad).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/futuros")
    @Operation(summary = "Listar eventos futuros")
    public List<EventDTO> getEventosFuturos() {
        return eventService.listarEventosFuturos().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/creador/{idUsuario}")
    @Operation(summary = "Obtener eventos creados por un usuario")
    public List<EventDTO> getEventosPorCreador(@PathVariable Integer idUsuario) {
        return eventService.obtenerEventosPorUsuarioCreador(idUsuario).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private EventDTO convertToDto(Event event) {
        if (event == null) return null;
        EventDTO dto = new EventDTO();
        dto.setIdEvento(event.getIdEvento());
        dto.setTitulo(event.getTitulo());
        dto.setDescripcion(event.getDescripcion());
        dto.setModalidad(event.getModalidad());
        dto.setFechaHora(event.getFechaHora());
        dto.setCupoMaximo(event.getCupoMaximo());
        dto.setUbicacion(event.getUbicacion());
        dto.setEnlaceVirtual(event.getEnlaceVirtual());
        dto.setEstado(event.getEstado());
        if (event.getIdioma() != null) {
            dto.setIdIdioma(event.getIdioma().getIdIdioma());
        }
        if (event.getOrganizador() != null) {
            dto.setIdOrganizador(event.getOrganizador().getIdUsuario());
        }
        return dto;
    }

    private Event convertToEntity(EventDTO dto) {
        if (dto == null) return null;
        Event event = new Event();
        event.setIdEvento(dto.getIdEvento());
        event.setTitulo(dto.getTitulo());
        event.setDescripcion(dto.getDescripcion());
        event.setModalidad(dto.getModalidad());
        event.setFechaHora(dto.getFechaHora());
        event.setCupoMaximo(dto.getCupoMaximo());
        event.setUbicacion(dto.getUbicacion());
        event.setEnlaceVirtual(dto.getEnlaceVirtual());
        event.setEstado(dto.getEstado());
        if (dto.getIdIdioma() != null) {
            Language language = new Language();
            language.setIdIdioma(dto.getIdIdioma());
            event.setIdioma(language);
        }
        if (dto.getIdOrganizador() != null) {
            User user = new User();
            user.setIdUsuario(dto.getIdOrganizador());
            event.setOrganizador(user);
        }
        return event;
    }
}
