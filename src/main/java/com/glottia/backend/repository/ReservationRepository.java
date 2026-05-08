package com.glottia.backend.repository;

import com.glottia.backend.entity.Reservation;
import com.glottia.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    default Reservation reservarEvento(Reservation reserva) {
        return save(reserva);
    }

    default void cancelarReserva(Integer idReserva) {
        deleteById(idReserva);
    }

    @Query("SELECT r FROM Reservation r WHERE r.usuario.idUsuario = ?1")
    List<Reservation> listarReservasPorUsuario(Integer idUsuario);

    @Query("SELECT r.usuario FROM Reservation r WHERE r.evento.idEvento = ?1")
    List<User> listarParticipantesPorEvento(Integer idEvento);

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.evento.idEvento = ?1")
    Long contarReservasPorEvento(Integer idEvento);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM Reservation r WHERE r.usuario.idUsuario = ?1 AND r.evento.idEvento = ?2")
    boolean validarReservaExistente(Integer idUsuario, Integer idEvento);
}

