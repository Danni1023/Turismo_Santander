package com.proyecto.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.proyecto.demo.models.Destino;
import com.proyecto.demo.models.Hotel;

public interface HotelRepository extends MongoRepository<Hotel, String> {

	List<Hotel> findByDestino(Destino destino);
	
	Hotel findByNombre(String nombre);

	@Query("{ 'nombre': { $regex: ?0, $options: 'i' } }")
	List<Hotel> searchHoteles(String query);

	void deleteByDestinoId(String id);

}
