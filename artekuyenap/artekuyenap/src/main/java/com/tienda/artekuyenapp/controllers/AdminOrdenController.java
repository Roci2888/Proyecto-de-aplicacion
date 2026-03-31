package com.tienda.artekuyenapp.controllers;

import com.tienda.artekuyenapp.models.orden.Orden;
import com.tienda.artekuyenapp.models.usuario.Rol;
import com.tienda.artekuyenapp.services.OrdenService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminOrdenController {

    private final OrdenService ordenService;

    public AdminOrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }


    @GetMapping("/admin/ordenes")
    public String listarOrdenes(Model model, HttpSession session) {

        String usuarioId = (String) session.getAttribute("usuarioId");
        Rol rol = (Rol) session.getAttribute("usuarioRol");

        if (usuarioId == null || rol == null || rol != Rol.ADMIN) {
            return "redirect:/login?mensaje=Debe iniciar sesión como administrador";
        }

        List<Orden> ordenes = ordenService.listarTodas();

        model.addAttribute("ordenes", ordenes);
        model.addAttribute("usuarioNombre", session.getAttribute("usuarioNombre"));

        //
        return "ordenes/ordenes";
    }
}
