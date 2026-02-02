package org.example.gestorparking.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    // Página de inicio - accesible sin autenticación
    @RequestMapping("/")
    String home(){
        return "home";
    }
}
