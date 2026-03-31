package com.tienda.artekuyenapp.repositories;

import com.tienda.artekuyenapp.models.usuario.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByEmail(String email);
}
