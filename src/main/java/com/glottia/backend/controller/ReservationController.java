package com.glottia.backend.controller;

import com.glottia.backend.entity.Reservation;
import com.glottia.backend.entity.User;
import com.glottia.backend.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Reservas", description = "Gestión de reservas de eventos")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    @Operation(summary = "Listar todas las reservas")
    public List<Reservation> getAllReservations() {
        return reservationService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una reserva por ID")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Integer id) {
        return reservationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear una nueva reserva")
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationService.save(reservation);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una reserva")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Integer id, @RequestBody Reservation reservation) {
        return reservationService.findById(id)
                .map(existingReservation -> {
                    reservation.setIdReserva(id);
                    return ResponseEntity.ok(reservationService.save(reservation));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{idUsuario}")
    @Operation(summary = "Listar reservas por usuario")
    public List<Reservation> getReservasPorUsuario(@PathVariable Integer idUsuario) {
        return reservationService.listarReservasPorUsuario(idUsuario);
    }

    @GetMapping("/evento/{idEvento}/participantes")
    @Operation(summary = "Listar participantes por evento")
    public List<User> getParticipantesPorEvento(@PathVariable Integer idEvento) {
        return reservationService.listarParticipantesPorEvento(idEvento);
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