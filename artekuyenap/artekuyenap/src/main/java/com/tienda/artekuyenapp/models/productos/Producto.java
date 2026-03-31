package com.tienda.artekuyenapp.models.productos;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "productos")
public abstract class Producto implements Descontable{

    @Id
    protected String id;
    protected String nombre;
    protected String descripcion;
    protected Double precioBase;
    protected Integer stock;
    protected String imagenUrl;

    protected Producto() {}

    protected Producto(String id, String nombre, String descripcion,
                       Double precioBase, Integer stock, String imagenUrl) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
        this.stock = stock;
        this.imagenUrl = imagenUrl;
    }

    public String resumen() {
        return nombre + " - $" + precioBase;
    }

    public void descontarStock(int cantidad) {
        if (cantidad <= 0) return;
        if (stock == null) stock = 0;
        if (stock < cantidad) {
            throw new IllegalArgumentException("Stock insuficiente para " + nombre);
        }
        stock -= cantidad;
    }

    public abstract String getTipo();
    public abstract boolean requiereEnvio();
    public abstract Double calcularPrecioFinal();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecioBase() { return precioBase; }
    public void setPrecioBase(Double precioBase) { this.precioBase = precioBase; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    // Implementación del método de la interface Descuento //
    @Override
    public double calcularPrecioConDescuento(double porcentajeDescuento) {
        if (porcentajeDescuento < 0 || porcentajeDescuento > 100) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100");
        }

        if (precioBase == null) {
            return 0.0;
        }

        double descuento = precioBase * (porcentajeDescuento / 100.0);
        return precioBase - descuento;
    }

}
