package org.example.gestorparking.Controllers;

import org.example.gestorparking.Models.User;
import org.example.gestorparking.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    // Mostrar página de login
    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }
        if (logout != null) {
            model.addAttribute("message", "Has cerrado sesión correctamente");
        }
        return "login";
    }

    // Mostrar página de registro
    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    // Procesar el registro
    @PostMapping("/registro")
    public String registrarUsuario(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            Model model) {
        try {
            // Validaciones básicas
            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                model.addAttribute("error", "Todos los campos son obligatorios");
                return "registro";
            }
            if (password.length() < 6) {
                model.addAttribute("error", "La contraseña debe tener al menos 6 caracteres");
                return "registro";
            }
            // Registrar el usuario
            userService.registrarUsuario(username, password, email);
            // Redirigir al login con mensaje de éxito
            model.addAttribute("message", "Usuario registrado correctamente. Ya puedes iniciar sesión.");
            return "login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }

    // Mostrar perfil del usuario autenticado
    @GetMapping("/userProfile")
    public String mostrarPerfil(Model model) {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Buscar el usuario en la base de datos
        Optional<User> usuario = userService.findByUsername(username);

        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
        }
        return "userProfile";
    }

    @PostMapping("/admin/delete-user")
    public String eliminarUsuario(@RequestParam Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/add-user")
    public String addUsuario(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("email") String email,
                             Model model) {

        try {
            // Validaciones básicas
            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                return "redirect:/admin/adminUsuarios";
            }
            if (password.length() < 6) {
                return "redirect:/admin/adminUsuarios";
            }
            // Registrar el usuario
            userService.registrarUsuario(username, password, email);

            // Redirigir al panel con mensaje de éxito
            return "redirect:/admin";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "/adminCRUD/adminUsuarios";
        }
    }
}