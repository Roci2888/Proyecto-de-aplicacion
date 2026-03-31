package com.tienda.artekuyenapp.models.orden;

import com.tienda.artekuyenapp.models.productos.Producto;

public class ItemCarrito {

    private String productoId;
    private String nombre;
    private String tipo;          // "FISICO" o "DIGITAL"
    private int cantidad;
    private Double precioUnitario;
    private Double subtotal;

    public ItemCarrito() {}

    public ItemCarrito(Producto producto, int cantidad) {
        this.productoId = producto.getId();
        this.nombre = producto.getNombre();
        this.tipo = producto.getTipo();
        this.precioUnitario = producto.getPrecioBase();
        this.cantidad = cantidad;
        recalcular();
    }

    public void incrementarCantidad(int cantidad) {
        this.cantidad += cantidad;
        recalcular();
    }

    public void recalcular() {
        if (precioUnitario == null) precioUnitario = 0.0;
        this.subtotal = precioUnitario * cantidad;
    }


    public String getProductoId() { return productoId; }
    public void setProductoId(String productoId) { this.productoId = productoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
}
