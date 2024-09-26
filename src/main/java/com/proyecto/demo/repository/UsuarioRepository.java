package com.proyecto.demo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyecto.demo.models.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario,String> {

	Optional<Usuario> findByUserAndPassword(String user, String password);

	Usuario findByDocumento(int documento);

}
