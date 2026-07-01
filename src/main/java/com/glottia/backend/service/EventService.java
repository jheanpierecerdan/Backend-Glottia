package com.glottia.backend.service;

import com.glottia.backend.entity.Event;
import com.glottia.backend.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Optional<Event> findById(Integer id) {
        return eventRepository.findById(id);
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public void deleteById(Integer id) {
        eventRepository.deleteById(id);
    }

    public List<Event> listarEventosActivos() {
        return eventRepository.listarEventosActivos();
    }

    public List<Event> buscarEventosPorIdioma(Integer idIdioma) {
        return eventRepository.buscarEventosPorIdioma(idIdioma);
    }

    public List<Event> buscarEventosPorModalidad(String modalidad) {
        return eventRepository.buscarEventosPorModalidad(modalidad);
    }

    public List<Event> listarEventosFuturos() {
        return eventRepository.listarEventosFuturos();
    }

    public List<Event> obtenerEventosPorUsuarioCreador(Integer idUsuario) {
        return eventRepository.obtenerEventosPorUsuarioCreador(idUsuario);
    }
}