package com.glottia.api.repository;

import com.glottia.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.correo = ?1")
    Optional<User> buscarPorCorreo(String correo);

    @Query("SELECT u FROM User u JOIN u.rol r WHERE r.nombre = ?1")
    List<User> listarUsuariosConRol(String nombreRol);

    @Query("SELECT u FROM User u WHERE u.ciudad = ?1")
    List<User> buscarPorCiudad(String ciudad);

    @Query("SELECT u FROM User u WHERE u.modalidad = ?1")
    List<User> buscarPorModalidad(String modalidad);
}
