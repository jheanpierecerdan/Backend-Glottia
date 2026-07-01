package com.glottia.backend.service;

import com.glottia.backend.entity.UserInterest;
import com.glottia.backend.repository.UserInterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInterestService {

    @Autowired
    private UserInterestRepository userInterestRepository;

    public List<UserInterest> findAll() {
        return userInterestRepository.findAll();
    }

    public Optional<UserInterest> findById(Integer id) {
        return userInterestRepository.findById(id);
    }

    public List<UserInterest> findByUsuarioId(Integer usuarioId) {
        return userInterestRepository.obtenerInteresesPorUsuario(usuarioId);
    }

    public UserInterest save(UserInterest userInterest) {
        return userInterestRepository.save(userInterest);
    }

    public void deleteById(Integer id) {
        userInterestRepository.deleteById(id);
    }

    public List<UserInterest> buscarUsuariosPorInteres(Integer idInteres) {
        return userInterestRepository.buscarUsuariosPorInteres(idInteres);
    }
}
