package com.proyecto.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyecto.demo.models.Actividad;
import com.proyecto.demo.models.Destino;

public interface ActividadesRepository extends MongoRepository<Actividad, String> {

	List<Actividad> findByDestino(Destino destino);

	void deleteByDestinoId(String id);

}
