package com.glottia.backend.service;

import com.glottia.backend.entity.User;
import com.glottia.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        if (user.getContrasena() == null || user.getContrasena().isBlank()) {
            if (user.getIdUsuario() != null) {
                userRepository.findById(user.getIdUsuario()).ifPresent(existing -> user.setContrasena(existing.getContrasena()));
            }
        } else if (!user.getContrasena().startsWith("$2")) {
            user.setContrasena(passwordEncoder.encode(user.getContrasena()));
        }
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