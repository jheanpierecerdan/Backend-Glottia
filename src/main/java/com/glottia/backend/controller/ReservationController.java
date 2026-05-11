package com.glottia.backend.controller;

import com.glottia.backend.dto.ReservationDTO;
import com.glottia.backend.dto.UserDTO;
import com.glottia.backend.entity.Reservation;
import com.glottia.backend.entity.User;
import com.glottia.backend.mapper.EntityMapper;
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

    @Autowired
    private EntityMapper mapper;

    @GetMapping
    @Operation(summary = "Listar todas las reservas")
    public List<ReservationDTO> getAllReservations() {
        return reservationService.findAll().stream()
                .map(mapper::toReservationDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una reserva por ID")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Integer id) {
        return reservationService.findById(id)
                .map(mapper::toReservationDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear una nueva reserva")
    public ReservationDTO createReservation(@RequestBody ReservationDTO reservationDto) {
        Reservation reservation = mapper.toReservationEntity(reservationDto);
        return mapper.toReservationDTO(reservationService.save(reservation));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una reserva")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable Integer id, @RequestBody ReservationDTO reservationDto) {
        return reservationService.findById(id)
                .map(existingReservation -> {
                    Reservation reservation = mapper.toReservationEntity(reservationDto);
                    reservation.setIdReserva(id);
                    return ResponseEntity.ok(mapper.toReservationDTO(reservationService.save(reservation)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{idUsuario}")
    @Operation(summary = "Listar reservas por usuario")
    public List<ReservationDTO> getReservasPorUsuario(@PathVariable Integer idUsuario) {
        return reservationService.listarReservasPorUsuario(idUsuario).stream()
                .map(mapper::toReservationDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/evento/{idEvento}/participantes")
    @Operation(summary = "Listar participantes por evento")
    public List<UserDTO> getParticipantesPorEvento(@PathVariable Integer idEvento) {
        return reservationService.listarParticipantesPorEvento(idEvento).stream()
                .map(mapper::toUserDTO)
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
}
