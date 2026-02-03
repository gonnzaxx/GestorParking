package org.example.gestorparking.Controllers;

import org.example.gestorparking.Models.Parking;
import org.example.gestorparking.Models.ParkingSpot;
import org.example.gestorparking.Models.Reservation;
import org.example.gestorparking.Models.User;
import org.example.gestorparking.Services.ParkingService;
import org.example.gestorparking.Services.ParkingSpotService;
import org.example.gestorparking.Services.ReservationService;
import org.example.gestorparking.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {


    @Autowired
    private UserService userService;

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ParkingSpotService parkingSpotService;

    // ========== PANEL PRINCIPAL ==========

    // Página principal del admin
    @GetMapping("/admin")
    public String adminPanel(Model model) {
        try {
            List<User> usuarios = userService.findAll();
            model.addAttribute("usuarios", usuarios);
            model.addAttribute("usuariosCount", usuarios.size());

            List<Parking> parkings = parkingService.findAll();
            model.addAttribute("parkings", parkings);
            model.addAttribute("parkingsCount", parkings.size());

            List<Reservation> reservations = reservationService.findAll();
            model.addAttribute("reservations", reservations);
            model.addAttribute("reservationsCount", reservations.size());

            List<ParkingSpot> parkingSpots = parkingSpotService.findAll();
            model.addAttribute("parkingSpots", parkingSpots);
            model.addAttribute("parkingSpotsCount", parkingSpots.size());
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al cargar datos");
        }
        return "admin";
    }

    // ========== CRUD USUARIOS ==========

    // Página de gestión de usuarios
    @GetMapping("/admin/usuarios")
    public String adminUsuarios(Model model) {
        try {
            List<User> usuarios = userService.findAll();
            model.addAttribute("usuarios", usuarios);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al cargar usuarios");
        }
        return "adminCRUD/adminUsuarios";
    }

    // Crear nuevo usuario
    @PostMapping("/add-user")
    public String addUsuario(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            @RequestParam(value = "role", defaultValue = "ROLE_USER") String role,
            Model model) {
        try {
            // Validaciones
            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                model.addAttribute("errorMessage", "Todos los campos son obligatorios");
                return "redirect:/admin/usuarios";
            }
            if (password.length() < 6) {
                model.addAttribute("errorMessage", "La contraseña debe tener al menos 6 caracteres");
                return "redirect:/admin/usuarios";
            }

            // Crear usuario
            User newUser = userService.registrarUsuario(username, password, email);
            newUser.setRole(role);
            userService.save(newUser);

            model.addAttribute("successMessage", "Usuario '" + username + "' creado correctamente");
            return "redirect:/admin/usuarios";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/usuarios";
        }
    }

    // Actualizar usuario (cambiar rol y enabled)
    @PostMapping("/admin/update-user")
    public String updateUsuario(
            @RequestParam("id") Long id,
            @RequestParam("role") String role,
            @RequestParam("enabled") Boolean enabled,
            Model model) {
        try {
            Optional<User> usuario = userService.findById(id);
            if (usuario.isPresent()) {
                User user = usuario.get();
                user.setRole(role);
                user.setEnabled(enabled);
                userService.save(user);
                model.addAttribute("successMessage", "Usuario actualizado correctamente");
            } else {
                model.addAttribute("errorMessage", "Usuario no encontrado");
            }
            return "redirect:/admin/usuarios";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al actualizar usuario: " + e.getMessage());
            return "redirect:/admin/usuarios";
        }
    }

    // Eliminar usuario
    @PostMapping("/delete-user")
    public String deleteUsuario(
            @RequestParam("id") Long id,
            Model model) {
        try {
            userService.deleteById(id);
            model.addAttribute("successMessage", "Usuario eliminado correctamente");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al eliminar usuario");
        }
        return "redirect:/admin/usuarios";
    }

    // Cambiar enabled de un usuario
    @PostMapping("/toggle-user/{id}")
    public String toggleUserEnabled(
            @PathVariable Long id,
            Model model) {
        try {
            Optional<User> usuario = userService.findById(id);
            if (usuario.isPresent()) {
                User user = usuario.get();
                user.setEnabled(!user.getEnabled());
                userService.save(user);
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al actualizar usuario");
        }
        return "redirect:/admin/usuarios";
    }

    // ========== CRUD PARKINGS ==========

    // Página de gestión de parkings
    @GetMapping("/admin/parkings")
    public String adminParkings(Model model) {
        try {
            List<Parking> parkings = parkingService.findAll();
            model.addAttribute("parkings", parkings);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al cargar parkings");
        }
        return "adminCRUD/adminParkings";
    }

    // Crear nuevo parking
    @PostMapping("/admin/add-parking")
    public String addParking(
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("pricePerHour") Double pricePerHour,
            @RequestParam("schedule") String schedule,
            @RequestParam("totalSpots") Integer totalSpots,
            Model model) {
        try {
            // Validaciones
            if (name.isEmpty() || address.isEmpty() || schedule.isEmpty()) {
                model.addAttribute("errorMessage", "Todos los campos son obligatorios");
                return "redirect:/admin/parkings";
            }
            if (pricePerHour < 0) {
                model.addAttribute("errorMessage", "El precio no puede ser negativo");
                return "redirect:/admin/parkings";
            }
            if (totalSpots < 1) {
                model.addAttribute("errorMessage", "Las plazas deben ser mínimo 1");
                return "redirect:/admin/parkings";
            }

            // Crear parking
            Parking parking = new Parking();
            parking.setName(name);
            parking.setAddress(address);
            parking.setPricePerHour(pricePerHour);
            parking.setSchedule(schedule);
            parkingService.save(parking);

            model.addAttribute("successMessage", "Parking '" + name + "' creado correctamente");
            return "redirect:/admin/parkings";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al crear parking: " + e.getMessage());
            return "redirect:/admin/parkings";
        }
    }

    // Actualizar parking
    @PostMapping("/admin/update-parking")
    public String updateParking(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("pricePerHour") Double pricePerHour,
            @RequestParam("schedule") String schedule,
            @RequestParam("spots") int spots,
            Model model) {
        try {
            Optional<Parking> parking = parkingService.findById(id);
            if (parking.isPresent()) {
                Parking p = parking.get();
                p.setName(name);
                p.setAddress(address);
                p.setPricePerHour(pricePerHour);
                p.setSchedule(schedule);
                p.setSpots(new ArrayList<>(spots));
                parkingService.save(p);
                model.addAttribute("successMessage", "Parking actualizado correctamente");
            } else {
                model.addAttribute("errorMessage", "Parking no encontrado");
            }
            return "redirect:/admin/parkings";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al actualizar parking: " + e.getMessage());
            return "redirect:/admin/parkings";
        }
    }

    // Eliminar parking
    @PostMapping("/admin/delete-parking")
    public String deleteParking(
            @RequestParam("id") Long id,
            Model model) {
        try {
            parkingService.deleteById(id);
            model.addAttribute("successMessage", "Parking eliminado correctamente");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al eliminar parking");
        }
        return "redirect:/admin/parkings";
    }

    // ========== CRUD DE RESERVAS ==========

    // Página de gestión de reservas
    @GetMapping("/admin/reservas")
    public String adminReservas(Model model) {
        try {
            List<Reservation> reservas = reservationService.findAll();
            model.addAttribute("reservas", reservas);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al cargar reservas");
        }
        return "adminCRUD/adminReservas";
    }


    // ========== CRUD DE PLAZAS ==========

    // Página de gestión de plazas
    @GetMapping("/admin/plazas")
    public String adminPlazas(Model model) {
        try {
            List<ParkingSpot> plazas = parkingSpotService.findAll();
            model.addAttribute("plazas", plazas);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al cargar plazas");
        }
        return "adminCRUD/adminPlazas";
    }


}
