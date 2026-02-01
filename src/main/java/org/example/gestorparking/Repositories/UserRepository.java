package org.example.gestorparking.Repositories;

import org.example.gestorparking.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}