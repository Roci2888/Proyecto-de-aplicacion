package com.tienda.artekuyenapp.repositories;

import com.tienda.artekuyenapp.models.orden.Carrito;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CarritoRepository extends MongoRepository<Carrito, String> {

    Optional<Carrito> findByUsuarioId(String usuarioId);
}
