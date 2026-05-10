package com.glottia.backend.repository;

import com.glottia.backend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("SELECT e FROM Event e WHERE e.estado = 'ACTIVO'")
    List<Event> listarEventosActivos();

    @Query("SELECT e FROM Event e WHERE e.idioma.idIdioma = ?1")
    List<Event> buscarEventosPorIdioma(Integer idIdioma);

    @Query("SELECT e FROM Event e WHERE e.modalidad = ?1")
    List<Event> buscarEventosPorModalidad(String modalidad);

    @Query("SELECT e FROM Event e WHERE e.fechaHora > CURRENT_TIMESTAMP")
    List<Event> listarEventosFuturos();

    @Query("SELECT e FROM Event e WHERE e.organizador.idUsuario = ?1")
    List<Event> obtenerEventosPorUsuarioCreador(Integer idUsuario);
}