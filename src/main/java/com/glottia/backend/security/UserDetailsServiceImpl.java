package com.glottia.backend.security;

import com.glottia.backend.entity.User;
import com.glottia.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        // En este proyecto, usamos el "correo" como el "username" para el login.
        // Como no existe un método findByCorreo, asumimos que existe o lo creamos.
        // Wait, I will need to add findByCorreo to UserRepository.
        Optional<User> userOptional = userRepository.buscarPorCorreo(correo);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado con correo: " + correo);
        }

        User user = userOptional.get();

        if (user.getRol() == null) {
            throw new UsernameNotFoundException("El usuario no tiene un rol asignado: " + correo);
        }

        String roleName = "ROLE_" + user.getRol().getNombre().toUpperCase();
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(roleName));

        return new org.springframework.security.core.userdetails.User(
                user.getCorreo(),
                user.getContrasena(),
                user.getEstado() != null ? user.getEstado() : true,
                true,
                true,
                true,
                authorities
        );
    }
}
