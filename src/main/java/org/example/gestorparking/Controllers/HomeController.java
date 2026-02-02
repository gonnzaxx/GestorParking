package org.example.gestorparking.Controllers;

import org.example.gestorparking.Models.Parking;
import org.example.gestorparking.Services.ParkingService;
import org.example.gestorparking.Services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private ParkingService parkingService;
    @Autowired
    private ReservationService reservationService;

    // Página de inicio
    @RequestMapping("/")
    String home(Model model){
        model.addAttribute("parkings", parkingService.findAll());
        return "home";
    }

    // Página de reservas
    @RequestMapping("/reservation")
    String reservation(Model model){
        //model.addAttribute("reservations", reservationService.findAll());
        return "reservation";
    }

    // Página de detalles del parking
    @RequestMapping("/detailsParking")
    String detailsParking(Model model){
        //model.addAttribute("reservations", reservationService.findAll());
        return "detailsParking";
    }

    //Página de admin
    @RequestMapping("/admin")
    String admin(){
        return "admin";
    }
}
