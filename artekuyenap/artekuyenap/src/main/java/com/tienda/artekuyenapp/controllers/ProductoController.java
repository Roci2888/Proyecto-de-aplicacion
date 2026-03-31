package com.tienda.artekuyenapp.controllers;

import com.tienda.artekuyenapp.services.ProductoService;
import com.tienda.artekuyenapp.models.productos.Producto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // responde a GET /productos
    @GetMapping("/productos")
    public String listarProductos(Model model, HttpSession session) {

        String usuarioId = (String) session.getAttribute("usuarioId");
        boolean logueado = (usuarioId != null);

        model.addAttribute("logueado", logueado);
        model.addAttribute("usuarioNombre", session.getAttribute("usuarioNombre"));

        model.addAttribute("productos", productoService.listarTodos());

        return "productos/lista";
    }

    // GET /productos/{id}
    @GetMapping("/productos/{id}")
    public String verDetalle(@PathVariable("id") String id,
                             Model model,
                             HttpSession session) {

        String usuarioId = (String) session.getAttribute("usuarioId");
        boolean logueado = (usuarioId != null);

        model.addAttribute("logueado", logueado);
        model.addAttribute("usuarioNombre", session.getAttribute("usuarioNombre"));

        Producto producto = productoService.buscarPorId(id);
        model.addAttribute("producto", producto);

        return "productos/detalle";
    }
}
