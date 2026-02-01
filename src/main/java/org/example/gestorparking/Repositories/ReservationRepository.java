package org.example.gestorparking.Repositories;

import org.example.gestorparking.Models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
