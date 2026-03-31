package com.tienda.artekuyenapp.repositories;

import com.tienda.artekuyenapp.models.orden.Orden;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrdenRepository extends MongoRepository<Orden, String> {

    List<Orden> findByUsuarioId(String usuarioId);
}
