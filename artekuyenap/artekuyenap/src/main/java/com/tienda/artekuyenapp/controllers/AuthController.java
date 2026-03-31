package com.tienda.artekuyenapp.controllers;

import com.tienda.artekuyenapp.models.usuario.LoginRequest;
import com.tienda.artekuyenapp.models.usuario.Usuario;
import com.tienda.artekuyenapp.models.usuario.Rol;
import com.tienda.artekuyenapp.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

// ...

@Controller
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "auth/login";
    }

    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute("loginRequest") LoginRequest loginRequest,
                                Model model,
                                HttpSession session) {

        System.out.println("=== LOGIN INTENT ===");
        System.out.println("Email recibido: " + loginRequest.getEmail());
        System.out.println("Password recibido: " + loginRequest.getPassword());

        var opt = usuarioRepository.findByEmail(loginRequest.getEmail());

        if (opt.isEmpty()) {
            System.out.println("No se encontró usuario con ese email");
            model.addAttribute("error", "Correo o contraseña incorrectos.");
            return "auth/login";
        }

        Usuario usuario = opt.get();
        System.out.println("Password en BD: " + usuario.getPasswordHash());

        if (!usuario.getPasswordHash().equals(loginRequest.getPassword())) {
            System.out.println("Contraseña NO coincide");
            model.addAttribute("error", "Correo o contraseña incorrectos.");
            return "auth/login";
        }

       /* se crea la sesión */
        session.setAttribute("usuarioId", usuario.getId());
        session.setAttribute("usuarioNombre", usuario.getNombre());
        session.setAttribute("usuarioRol", usuario.getRol());

        System.out.println("Login OK. Sesión creada: usuarioId=" + usuario.getId());

        return "redirect:/productos";
    }


   /* Mostrar formulario de registro */
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }

   /* Procesa el formulario de registro */
    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute("usuario") Usuario formUsuario,
                                   Model model,
                                   HttpSession session) {

       /* revisando si existe un usuario con ese email */
        Optional<Usuario> existente = usuarioRepository.findByEmail(formUsuario.getEmail());
        if (existente.isPresent()) {
            model.addAttribute("error", "Ya existe una cuenta con ese correo.");
            return "auth/registro";
        }

        /* creando un nuevo usuario */
        Usuario nuevo = new Usuario();
        nuevo.setNombre(formUsuario.getNombre());
        nuevo.setEmail(formUsuario.getEmail());

        nuevo.setPasswordHash(formUsuario.getPasswordHash());
        nuevo.setRol(Rol.CLIENTE);
        nuevo.setDireccion(formUsuario.getDireccion());
        nuevo = usuarioRepository.save(nuevo);

       /* El usuario queda logueado inmediatamente después de rgistrarse */
        session.setAttribute("usuarioId", nuevo.getId());
        session.setAttribute("usuarioNombre", nuevo.getNombre());
        session.setAttribute("usuarioRol", nuevo.getRol());

        /*Vuelve a la lista de productos */
        return "redirect:/productos";
    }

    @GetMapping("/panel")
    public String verPanel(Model model, HttpSession session) {
        String usuarioId = (String) session.getAttribute("usuarioId");
        Rol rol = (Rol) session.getAttribute("usuarioRol");

        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        model.addAttribute("logueado", true);
        model.addAttribute("usuarioNombre", session.getAttribute("usuarioNombre"));
        model.addAttribute("esAdmin", rol == Rol.ADMIN);

        return "panel/panel";
    }



   /* salir de la sesión */

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
