package org.example.gestorparking.Services;

import org.example.gestorparking.Models.Parking;
import org.example.gestorparking.Repositories.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingService {

    @Autowired
    private ParkingRepository parkingRepository;

    // Crear/Actualizar parking
    public Parking save(Parking parking) {
        return parkingRepository.save(parking);
    }

    // Buscar parking por ID
    public Optional<Parking> findById(Long id) {
        return parkingRepository.findById(id);
    }

    // Obtener todos los parkings
    public List<Parking> findAll() {
        return parkingRepository.findAll();
    }

    // Eliminar parking por ID
    public void deleteById(Long id) {
        parkingRepository.deleteById(id);
    }

    // Eliminar parking
    public void delete(Parking parking) {
        parkingRepository.delete(parking);
    }

    // Contar parkings
    public long count() {
        return parkingRepository.count();
    }
}