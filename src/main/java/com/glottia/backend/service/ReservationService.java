package com.glottia.backend.service;

import com.glottia.backend.entity.Reservation;
import com.glottia.backend.entity.User;
import com.glottia.backend.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> findById(Integer id) {
        return reservationRepository.findById(id);
    }

    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public void deleteById(Integer id) {
        reservationRepository.deleteById(id);
    }

    public List<Reservation> listarReservasPorUsuario(Integer idUsuario) {
        return reservationRepository.listarReservasPorUsuario(idUsuario);
    }

    public List<User> listarParticipantesPorEvento(Integer idEvento) {
        return reservationRepository.listarParticipantesPorEvento(idEvento);
    }

    public Long contarReservasPorEvento(Integer idEvento) {
        return reservationRepository.contarReservasPorEvento(idEvento);
    }

    public boolean validarReservaExistente(Integer idUsuario, Integer idEvento) {
        return reservationRepository.validarReservaExistente(idUsuario, idEvento);
    }
}

