package com.tienda.artekuyenapp.models.productos;


public class ProductoDigital extends Producto {

    private String urlDescarga;

    public ProductoDigital() {}

    public ProductoDigital(String id, String nombre, String descripcion,
                           Double precioBase,
                           String imagenUrl,
                           String urlDescarga) {
        super(id, nombre, descripcion, precioBase, null, imagenUrl);
        this.urlDescarga = urlDescarga;
    }

    @Override
    public String getTipo() { return "DIGITAL"; }
    @Override
    public boolean requiereEnvio() { return false; }
    @Override
    public Double calcularPrecioFinal() { return precioBase; }





    public String getUrlDescarga() { return urlDescarga; }
    public void setUrlDescarga(String urlDescarga) { this.urlDescarga = urlDescarga; }
}
