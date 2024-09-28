package com.proyecto.demo.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.demo.models.Admin;
import com.proyecto.demo.models.Destino;
import com.proyecto.demo.models.Hotel;
import com.proyecto.demo.models.Restaurante;
import com.proyecto.demo.models.Usuario;
import com.proyecto.demo.repository.DestinoRepository;
import com.proyecto.demo.repository.HotelRepository;
import com.proyecto.demo.repository.RestauranteRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

	@Autowired
	private DestinoRepository destinoRepository;
	
	@Autowired
	private HotelRepository hotelRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	//Método para traer los destinos favoritos del usuario
	@GetMapping
	public String destinosFavoritos(Model model, HttpServletRequest request) {
		List<Destino> destinos = destinoRepository.findAll();
		
		Usuario usuario = (Usuario) request.getSession().getAttribute("UserLog");
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		String userId = (usuario != null) ? usuario.getId() : (admin != null ? admin.getId() : null);
		
		List<Destino> destinosFavoritos = destinos.stream()
				.filter(destino -> destino.getFavoritosUsuarios() != null && destino.getFavoritosUsuarios().contains("66bcbe4581116333a84432b3"))
				.collect(Collectors.toList());
		
		for (Destino destino : destinosFavoritos) {
			if (destino.getImagenes() != null && !destino.getImagenes().isEmpty()) {
				List<String> imagenesBase64 = new ArrayList<>();
				for (byte[] imagen : destino.getImagenes()) {
					String imagenBase64 = Base64.getEncoder().encodeToString(imagen);
					imagenesBase64.add(imagenBase64);
				}
				destino.setImagenBase64(imagenesBase64);
			}
			destino.setFavoritos(destino.getFavoritosUsuarios() != null && destino.getFavoritosUsuarios().contains(userId));
		}
		model.addAttribute("destinos", destinosFavoritos);
		return "index";
	}
	
	//Método para traer todos los destinos
	@GetMapping("/destinos")
	public String destinos(Model model, HttpServletRequest request) {
		Usuario usuario = (Usuario) request.getSession().getAttribute("UserLog");
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		String userId = (usuario != null) ? usuario.getId() : (admin != null ? admin.getId() : null);
		
		List<Destino> destinos = destinoRepository.findAll();
		
		for (Destino destino : destinos) {
			if (destino.getImagenes() != null && !destino.getImagenes().isEmpty()) {
				List<String> imagenesBase64 = new ArrayList<>();
				for (byte[] imagen : destino.getImagenes()) {
					String imagenBase64 = Base64.getEncoder().encodeToString(imagen);
					imagenesBase64.add(imagenBase64);
				}
				destino.setImagenBase64(imagenesBase64);
			}
			destino.setFavoritos(destino.getFavoritosUsuarios() != null && destino.getFavoritosUsuarios().contains(userId));
		}
		model.addAttribute("UserLog", usuario);
		model.addAttribute("AdminLog", admin);
		model.addAttribute("destinos", destinos);
		return "Destino/destinos";
	}
	
	//Método para realizar búsquedas
	@GetMapping("/search")
	public String search(@RequestParam("query") String query, Model model) {
		List<Destino> destinos = destinoRepository.searchDestinos(query);
		
		for (Destino destino : destinos) {
			if (destino.getImagenes() != null && !destino.getImagenes().isEmpty()) {
				List<String> imagenesBase64 = new ArrayList<>();
				for (byte[] imagen : destino.getImagenes()) {
					String imagenBase64 = Base64.getEncoder().encodeToString(imagen);
					imagenesBase64.add(imagenBase64);
				}
				destino.setImagenBase64(imagenesBase64);
			}
		}
		
		List<Hotel> hoteles = hotelRepository.searchHoteles(query);
		
		for (Hotel hotel : hoteles) {
			if (hotel.getImagenes() != null && !hotel.getImagenes().isEmpty()) {
				List<String> imagenesBase64 = new ArrayList<>();
				
				for (byte[] imagen : hotel.getImagenes()) {
					String imagenBase64 = Base64.getEncoder().encodeToString(imagen);
					imagenesBase64.add(imagenBase64);
				}
				hotel.setImagenBase64(imagenesBase64);
			}
		}
		
		List<Restaurante> restaurantes = restauranteRepository.searchRestaurantes(query);
		
		for (Restaurante restaurante : restaurantes) {
			if (restaurante.getImagenes() != null && !restaurante.getImagenes().isEmpty()) {
				List<String> imagenesBase64 = new ArrayList<>();
				
				for (byte[] imagen : restaurante.getImagenes()) {
					String imagenBase64 = Base64.getEncoder().encodeToString(imagen);
					imagenesBase64.add(imagenBase64);
				}
				restaurante.setImagenBase64(imagenesBase64);
			}
		}
		
		model.addAttribute("destinos", destinos);
		model.addAttribute("hoteles", hoteles);
		model.addAttribute("restaurantes", restaurantes);
		model.addAttribute("query", query);
		
		return "results";
	}
	
	//Método para navegar a la pestaña sobre nosotros
	@GetMapping("/sobre-nosotros")
	public String sobreNosotros() {
		return "nosotros";
	}
}
