package com.tienda.artekuyenapp.models.productos;

public class ProductoFisico extends Producto {

    private CategoriaProducto categoria;
    private Double pesoKg;

    public ProductoFisico() {}

    public ProductoFisico(String id, String nombre, String descripcion,
                          Double precioBase, Integer stock,
                          CategoriaProducto categoria,
                          Double pesoKg,
                          String imagenUrl) {

        super(id, nombre, descripcion, precioBase, stock, imagenUrl);
        this.categoria = categoria;
        this.pesoKg = pesoKg;
    }


    @Override
    public String getTipo() { return "FISICO"; }

    @Override
    public boolean requiereEnvio() { return true; }

    @Override
    public Double calcularPrecioFinal() {
        return precioBase; // luego puedes sumar IVA, envío, etc.
    }

    public CategoriaProducto getCategoria() { return categoria; }
    public void setCategoria(CategoriaProducto categoria) { this.categoria = categoria; }

    public Double getPesoKg() { return pesoKg; }
    public void setPesoKg(Double pesoKg) { this.pesoKg = pesoKg; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
}