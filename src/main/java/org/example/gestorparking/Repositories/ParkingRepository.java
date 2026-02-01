package org.example.gestorparking.Repositories;

import org.example.gestorparking.Models.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking, Long>{
}
