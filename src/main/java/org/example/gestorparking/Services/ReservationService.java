package org.example.gestorparking.Services;

import org.example.gestorparking.Models.Parking;
import org.example.gestorparking.Models.Reservation;
import org.example.gestorparking.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation create(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElseThrow();
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }
}
