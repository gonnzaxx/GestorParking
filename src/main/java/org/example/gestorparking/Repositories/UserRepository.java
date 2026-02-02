package org.example.gestorparking.Repositories;

import org.example.gestorparking.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Buscar usuario por nombre de usuario
    Optional<User> findByUsername(String username);

    // Verificar si existe un usuario con ese username
    Boolean existsByUsername(String username);

    // Verificar si existe un usuario con ese email
    Boolean existsByEmail(String email);
}