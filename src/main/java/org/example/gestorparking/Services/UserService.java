package org.example.gestorparking.Services;

import org.example.gestorparking.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.gestorparking.Models.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    // Registrar un nuevo usuario
    public User registrarUsuario(String username, String password, String email) {

        // Verificar si el usuario ya existe
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Crear el usuario
        User usuario = new User();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password)); //Encriptar contraseña
        usuario.setEmail(email);
        usuario.setRole("ROLE_USER");
        usuario.setEnabled(true);
        return userRepository.save(usuario);
    }

    // Buscar usuario por username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Verificar si existe el username
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    // Verificar si existe el email
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
