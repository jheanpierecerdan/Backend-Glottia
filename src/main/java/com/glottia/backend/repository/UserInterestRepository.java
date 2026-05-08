package com.glottia.backend.repository;

import com.glottia.backend.entity.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInterestRepository extends JpaRepository<UserInterest, Integer> {

    @Query("SELECT ui FROM UserInterest ui WHERE ui.usuario.idUsuario = ?1")
    List<UserInterest> obtenerInteresesPorUsuario(Integer idUsuario);

    @Query("SELECT ui FROM UserInterest ui WHERE ui.interes.idInteres = ?1")
    List<UserInterest> buscarUsuariosPorInteres(Integer idInteres);
}
