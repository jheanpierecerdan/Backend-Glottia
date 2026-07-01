package com.glottia.backend.controller;

import com.glottia.backend.dto.ReservationDTO;
import com.glottia.backend.dto.UserDTO;
import com.glottia.backend.entity.Event;
import com.glottia.backend.entity.Reservation;
import com.glottia.backend.entity.User;
import com.glottia.backend.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Reservas", description = "Gestión de reservas de eventos")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    @Operation(summary = "Listar todas las reservas")
    public List<ReservationDTO> getAllReservations() {
        return reservationService.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una reserva por ID")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Integer id) {
        return reservationService.findById(id)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear una nueva reserva")
    public ReservationDTO createReservation(@RequestBody ReservationDTO reservationDto) {
        Reservation reservation = convertToEntity(reservationDto);
        return convertToDto(reservationService.save(reservation));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una reserva")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable Integer id, @RequestBody ReservationDTO reservationDto) {
        return reservationService.findById(id)
                .map(existingReservation -> {
                    Reservation reservation = convertToEntity(reservationDto);
                    reservation.setIdReserva(id);
                    return ResponseEntity.ok(convertToDto(reservationService.save(reservation)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{idUsuario}")
    @Operation(summary = "Listar reservas por usuario")
    public List<ReservationDTO> getReservasPorUsuario(@PathVariable Integer idUsuario) {
        return reservationService.listarReservasPorUsuario(idUsuario).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/evento/{idEvento}/participantes")
    @Operation(summary = "Listar participantes por evento")
    public List<UserDTO> getParticipantesPorEvento(@PathVariable Integer idEvento) {
        return reservationService.listarParticipantesPorEvento(idEvento).stream()
                .map(this::convertToUserDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/evento/{idEvento}/conteo")
    @Operation(summary = "Contar reservas por evento")
    public Long contarReservasPorEvento(@PathVariable Integer idEvento) {
        return reservationService.contarReservasPorEvento(idEvento);
    }

    @GetMapping("/validar/{idUsuario}/{idEvento}")
    @Operation(summary = "Validar si existe reserva de un usuario para un evento")
    public boolean validarReservaExistente(@PathVariable Integer idUsuario, @PathVariable Integer idEvento) {
        return reservationService.validarReservaExistente(idUsuario, idEvento);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una reserva")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private ReservationDTO convertToDto(Reservation reservation) {
        if (reservation == null) return null;
        ReservationDTO dto = new ReservationDTO();
        dto.setIdReserva(reservation.getIdReserva());
        dto.setFechaReserva(reservation.getFechaReserva());
        dto.setEstadoReserva(reservation.getEstadoReserva());
        if (reservation.getUsuario() != null) {
            dto.setIdUsuario(reservation.getUsuario().getIdUsuario());
        }
        if (reservation.getEvento() != null) {
            dto.setIdEvento(reservation.getEvento().getIdEvento());
        }
        return dto;
    }

    private Reservation convertToEntity(ReservationDTO dto) {
        if (dto == null) return null;
        Reservation reservation = new Reservation();
        reservation.setIdReserva(dto.getIdReserva());
        reservation.setFechaReserva(dto.getFechaReserva());
        reservation.setEstadoReserva(dto.getEstadoReserva());
        if (dto.getIdUsuario() != null) {
            User user = new User();
            user.setIdUsuario(dto.getIdUsuario());
            reservation.setUsuario(user);
        }
        if (dto.getIdEvento() != null) {
            Event event = new Event();
            event.setIdEvento(dto.getIdEvento());
            reservation.setEvento(event);
        }
        return reservation;
    }

    private UserDTO convertToUserDto(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setIdUsuario(user.getIdUsuario());
        dto.setNombre(user.getNombre());
        dto.setApellido(user.getApellido());
        dto.setCorreo(user.getCorreo());
        dto.setContrasena(user.getContrasena());
        dto.setCiudad(user.getCiudad());
        dto.setBiografia(user.getBiografia());
        dto.setModalidad(user.getModalidad());
        dto.setFechaRegistro(user.getFechaRegistro());
        dto.setEstado(user.getEstado());
        if (user.getRol() != null) {
            dto.setIdRol(user.getRol().getIdRol());
        }
        return dto;
    }
}
