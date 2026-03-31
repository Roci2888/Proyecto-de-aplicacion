package com.tienda.artekuyenapp.services;

import com.tienda.artekuyenapp.models.orden.Carrito;
import com.tienda.artekuyenapp.models.orden.Orden;
import com.tienda.artekuyenapp.models.usuario.Usuario;
import com.tienda.artekuyenapp.repositories.OrdenRepository;
import com.tienda.artekuyenapp.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final CarritoService carritoService;
    private final UsuarioRepository usuarioRepository;

    public OrdenService(OrdenRepository ordenRepository,
                        CarritoService carritoService,
                        UsuarioRepository usuarioRepository) {
        this.ordenRepository = ordenRepository;
        this.carritoService = carritoService;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Crea una orden a partir del carrito del usuario y la guarda en MongoDB.
     *  Requiere que el usuario esté logueado
     */
    public Orden crearOrdenDesdeCarrito(String usuarioId) {

        // se obtiene el carrito del usuario
        Carrito carrito = carritoService.obtenerCarrito(usuarioId);

        if (carrito == null || carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new IllegalStateException("El carrito está vacío, no se puede crear la orden.");
        }

        //calcula total
        Double total = carritoService.calcularTotal(carrito);

        //Obtener usuario y su dirección
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado para la orden"));

        // 4) Construir la orden
        Orden orden = new Orden();
        orden.setUsuarioId(usuarioId);
        orden.setItems(carrito.getItems());
        orden.setTotal(total);
        orden.setEstado("CREADA");
        orden.setDireccion(usuario.getDireccion());

        // Guardar en MongoDB en la colección ordenes
        return ordenRepository.save(orden);
    }

    /*
     muestra todas las ordenes en el panel del admin
     */
    public List<Orden> listarTodas() {
        return ordenRepository.findAll();
    }
}
