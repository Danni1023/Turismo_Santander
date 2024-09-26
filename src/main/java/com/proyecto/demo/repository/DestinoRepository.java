package com.proyecto.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.proyecto.demo.models.Destino;

public interface DestinoRepository extends MongoRepository<Destino, String> {

	Destino findByNombre(String nombre);

	@Query("{ 'nombre': { $regex: ?0, $options: 'i' } }")
	List<Destino> searchDestinos(String query);

}
