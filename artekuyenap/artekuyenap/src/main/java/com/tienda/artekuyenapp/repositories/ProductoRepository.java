package com.tienda.artekuyenapp.repositories;

import com.tienda.artekuyenapp.models.productos.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductoRepository extends MongoRepository<Producto, String> {
}
