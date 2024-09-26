package com.proyecto.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.proyecto.demo.models.Destino;
import com.proyecto.demo.models.Restaurante;

public interface RestauranteRepository extends MongoRepository<Restaurante, String> {

	List<Restaurante> findByDestino(Destino destino);
	
	Restaurante findByNombre(String nombre);

	@Query("{ 'nombre': { $regex: ?0, $options: 'i' } }")
	List<Restaurante> searchRestaurantes(String query);

	void deleteByDestinoId(String id);
	
}
