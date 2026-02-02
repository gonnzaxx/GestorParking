package org.example.gestorparking.Controllers;

import org.example.gestorparking.Models.Parking;
import org.example.gestorparking.Services.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    // Mostrar detalles de un parking específico
    @GetMapping("/detailsParking/{id}")
    public String mostrarDetallesParking(@PathVariable Long id, Model model) {
        // Buscar el parking por ID
        Parking parking = parkingService.findById(id);

        if (parking == null) {
            return "redirect:/";  // Si no existe, vuelve a home
        }

        model.addAttribute("parking", parking);
        return "detailsParking";
    }

}
