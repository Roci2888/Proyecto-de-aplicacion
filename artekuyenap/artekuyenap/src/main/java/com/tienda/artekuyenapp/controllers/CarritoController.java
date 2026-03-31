package com.tienda.artekuyenapp.controllers;

import com.tienda.artekuyenapp.models.orden.Carrito;
import com.tienda.artekuyenapp.models.orden.Orden;
import com.tienda.artekuyenapp.services.CarritoService;
import com.tienda.artekuyenapp.services.OrdenService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CarritoController {

    private final CarritoService carritoService;
    private final OrdenService ordenService;

    public CarritoController(CarritoService carritoService,
                             OrdenService ordenService) {
        this.carritoService = carritoService;
        this.ordenService = ordenService;
    }

    // POST /carrito/agregar/{productoId}
    @PostMapping("/carrito/agregar/{productoId}")
    public String agregarAlCarrito(@PathVariable String productoId, HttpSession session) {
        String usuarioId = (String) session.getAttribute("usuarioId");
        if (usuarioId == null) {
            usuarioId = session.getId();   // invitado
        }
        carritoService.agregarProducto(usuarioId, productoId, 1);
        return "redirect:/carrito";
    }

    // GET /carrito
    @GetMapping("/carrito")
    public String verCarrito(Model model, HttpSession session) {
        String usuarioId = (String) session.getAttribute("usuarioId");
        if (usuarioId == null) {
            usuarioId = session.getId();   // invitado
        }

        Carrito carrito = carritoService.obtenerCarrito(usuarioId);
        Double total = carritoService.calcularTotal(carrito);

        boolean logueado = (session.getAttribute("usuarioId") != null);

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);
        model.addAttribute("logueado", logueado);
        model.addAttribute("usuarioNombre", session.getAttribute("usuarioNombre"));

        return "carrito/ver";
    }

    // POST /carrito/limpiar
    @PostMapping("/carrito/limpiar")
    public String limpiarCarrito(HttpSession session) {
        String usuarioId = (String) session.getAttribute("usuarioId");
        if (usuarioId == null) {
            usuarioId = session.getId();   // invitado también puede limpiar
        }
        carritoService.limpiarCarrito(usuarioId);
        return "redirect:/carrito";
    }

    // GET /carrito/finalizar
    @GetMapping("/carrito/finalizar")
    public String finalizarCompra(HttpSession session, Model model) {

        String usuarioId = (String) session.getAttribute("usuarioId");


        if (usuarioId == null) {
            return "redirect:/login?mensaje=Debe iniciar sesión para finalizar la compra";
        }

        // Crear la orden en la colección 'ordenes'
        Orden orden = ordenService.crearOrdenDesdeCarrito(usuarioId);

        // Limpiar el carrito del usuario
        carritoService.limpiarCarrito(usuarioId);

        // Pasar la orden a la vista
        model.addAttribute("orden", orden);

        return "carrito/finalizar";  // templates/carrito/finalizar.html
    }
}
