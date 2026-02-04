package org.example.gestorparking.Services;

import org.example.gestorparking.Models.Parking;
import org.example.gestorparking.Models.ParkingSpot;
import org.example.gestorparking.Repositories.ParkingRepository;
import org.example.gestorparking.Repositories.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSpotService {

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    public ParkingSpot create(ParkingSpot parkingSpot) {
        return parkingSpotRepository.save(parkingSpot);
    }

    public ParkingSpot save(ParkingSpot parkingSpot) {
        return parkingSpotRepository.save(parkingSpot);
    }

    public void delete(Long id) {
        parkingSpotRepository.deleteById(id);
    }

    public Optional<ParkingSpot> findById(Long id) {
        return parkingSpotRepository.findById(id);
    }

    public List<ParkingSpot> findAll() {
        return parkingSpotRepository.findAll();
    }

    public List<ParkingSpot> findAvailableSpots() {
        return parkingSpotRepository.findByOccupiedFalse();
    }

}
