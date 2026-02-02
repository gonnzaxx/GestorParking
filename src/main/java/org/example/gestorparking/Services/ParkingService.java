package org.example.gestorparking.Services;

import org.example.gestorparking.Models.Parking;
import org.example.gestorparking.Repositories.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingService {

    @Autowired
    private ParkingRepository parkingRepository;

    public Parking create(Parking parking) {
        return parkingRepository.save(parking);
    }

    public void delete(Long id) {
        parkingRepository.deleteById(id);
    }

    public Parking findById(Long id) {
        return parkingRepository.findById(id).orElseThrow();
    }

    public List<Parking> findAll() {
        return parkingRepository.findAll();
    }

}
