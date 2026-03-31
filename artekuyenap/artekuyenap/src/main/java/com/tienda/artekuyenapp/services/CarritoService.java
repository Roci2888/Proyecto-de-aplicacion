package com.tienda.artekuyenapp.services;

import com.tienda.artekuyenapp.models.orden.Carrito;
import com.tienda.artekuyenapp.models.orden.ItemCarrito;
import com.tienda.artekuyenapp.models.productos.Producto;
import com.tienda.artekuyenapp.repositories.CarritoRepository;
import com.tienda.artekuyenapp.repositories.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;

    public CarritoService(CarritoRepository carritoRepository,
                          ProductoRepository productoRepository) {
        this.carritoRepository = carritoRepository;
        this.productoRepository = productoRepository;
    }

    //  Obtener el carrito del usuario o crearlo si no existe
    public Carrito obtenerCarrito(String usuarioId) {
        Optional<Carrito> opt = carritoRepository.findByUsuarioId(usuarioId);

        return opt.orElseGet(() -> {
            Carrito nuevo = new Carrito(usuarioId);
            return carritoRepository.save(nuevo);
        });
    }

    //  Agregar producto al carrito
    public void agregarProducto(String usuarioId, String productoId, int cantidad) {
        if (cantidad <= 0) return;

        Carrito carrito = obtenerCarrito(usuarioId);

        // buscar producto puede ser físico o digital
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (carrito.getItems() == null) {
            carrito.setItems(new ArrayList<>());
        }

        // ver si ya está el producto en el carrito
        ItemCarrito existente = carrito.getItems().stream()
                .filter(i -> i.getProductoId().equals(productoId))
                .findFirst()
                .orElse(null);

        if (existente != null) {
            existente.incrementarCantidad(cantidad);
        } else {
            ItemCarrito nuevo = new ItemCarrito(producto, cantidad);
            carrito.getItems().add(nuevo);
        }

        carritoRepository.save(carrito);
    }

    // Calcular total
    public Double calcularTotal(Carrito carrito) {
        if (carrito.getItems() == null) return 0.0;
        return carrito.getItems().stream()
                .mapToDouble(i -> i.getSubtotal() != null ? i.getSubtotal() : 0.0)
                .sum();
    }

    // Limpiar carrito
    public void limpiarCarrito(String usuarioId) {
        Carrito carrito = obtenerCarrito(usuarioId);
        carrito.setItems(new ArrayList<>());
        carritoRepository.save(carrito);
    }
}
