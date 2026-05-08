package com.glottia.backend.repository;

import com.glottia.backend.entity.UserLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLanguageRepository extends JpaRepository<UserLanguage, Integer> {

    @Query("SELECT ul FROM UserLanguage ul WHERE ul.usuario.idUsuario = ?1")
    List<UserLanguage> obtenerIdiomasPorUsuario(Integer idUsuario);

    @Query("SELECT ul FROM UserLanguage ul WHERE ul.idioma.idIdioma = ?1")
    List<UserLanguage> buscarUsuariosPorIdioma(Integer idIdioma);

    @Query("SELECT ul FROM UserLanguage ul WHERE ul.idioma.idIdioma = ?1 AND ul.nivel = ?2")
    List<UserLanguage> buscarUsuariosPorIdiomaYNivel(Integer idIdioma, String nivel);
}
