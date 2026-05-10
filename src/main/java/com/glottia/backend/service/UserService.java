package com.glottia.backend.service;

import com.glottia.backend.entity.User;
import com.glottia.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByCorreo(String correo) {
        return userRepository.buscarPorCorreo(correo);
    }

    public List<User> listarUsuariosConRol(String nombreRol) {
        return userRepository.listarUsuariosConRol(nombreRol);
    }

    public List<User> buscarPorCiudad(String ciudad) {
        return userRepository.buscarPorCiudad(ciudad);
    }

    public List<User> buscarPorModalidad(String modalidad) {
        return userRepository.buscarPorModalidad(modalidad);
    }
}