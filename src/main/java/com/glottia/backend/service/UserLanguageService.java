package com.glottia.backend.service;

import com.glottia.backend.entity.UserLanguage;
import com.glottia.backend.repository.UserLanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserLanguageService {

    @Autowired
    private UserLanguageRepository userLanguageRepository;

    public List<UserLanguage> findAll() {
        return userLanguageRepository.findAll();
    }

    public Optional<UserLanguage> findById(Integer id) {
        return userLanguageRepository.findById(id);
    }

    public UserLanguage save(UserLanguage userLanguage) {
        return userLanguageRepository.save(userLanguage);
    }

    public void deleteById(Integer id) {
        userLanguageRepository.deleteById(id);
    }

    public List<UserLanguage> obtenerIdiomasPorUsuario(Integer idUsuario) {
        return userLanguageRepository.obtenerIdiomasPorUsuario(idUsuario);
    }

    public List<UserLanguage> buscarUsuariosPorIdioma(Integer idIdioma) {
        return userLanguageRepository.buscarUsuariosPorIdioma(idIdioma);
    }

    public List<UserLanguage> buscarUsuariosPorIdiomaYNivel(Integer idIdioma, String nivel) {
        return userLanguageRepository.buscarUsuariosPorIdiomaYNivel(idIdioma, nivel);
    }
}
