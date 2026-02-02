package org.example.gestorparking.Controllers;

import org.example.gestorparking.Models.Parking;
import org.example.gestorparking.Services.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class ReservationController {

    @Autowired
    private ParkingService parkingService;

    // Página de reserva - recibe el ID del parking como parámetro
    @GetMapping("/reservation")
    public String mostrarReserva(@RequestParam(required = false) Long parkingId, Model model) {

        // Si viene un ID de parking, buscar ese parking
        if (parkingId != null) {
            Parking parking = parkingService.findById(parkingId);
            if (parking != null) {
                model.addAttribute("parking", parking);
                model.addAttribute("parkingId", parkingId);
            }
        }

        // Pasar todos los parkings por si quiere cambiar
        model.addAttribute("parkings", parkingService.findAll());

        return "reservation";
    }
}