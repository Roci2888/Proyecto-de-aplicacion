package com.tienda.artekuyenapp.models.productos;

public interface Descontable {
    /*
      Calcula el precio aplicando un porcentaje de descuento.
     */
    double calcularPrecioConDescuento(double porcentajeDescuento);
}
