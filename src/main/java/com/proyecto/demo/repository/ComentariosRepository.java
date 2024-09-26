package com.proyecto.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyecto.demo.models.Comentarios;
import com.proyecto.demo.models.Destino;
import com.proyecto.demo.models.Hotel;
import com.proyecto.demo.models.Restaurante;

public interface ComentariosRepository extends MongoRepository<Comentarios, String> {

	List<Comentarios> findByDestino(Destino destino);

	List<Comentarios> findByHotel(Hotel hoteles);

	List<Comentarios> findByRestaurante(Restaurante restaurantes);

	void deleteByDestinoId(String id);

	void deleteByHotelId(String id);

	void deleteByRestauranteId(String id);

	void deleteByAdminId(String id);

	void deleteByUsuarioId(String id);

}
