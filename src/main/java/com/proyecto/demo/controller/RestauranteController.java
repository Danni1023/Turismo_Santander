package com.proyecto.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.demo.DTO.RestauranteDTO;
import com.proyecto.demo.models.Admin;
import com.proyecto.demo.models.Comentarios;
import com.proyecto.demo.models.Destino;
import com.proyecto.demo.models.Restaurante;
import com.proyecto.demo.models.Usuario;
import com.proyecto.demo.repository.ComentariosRepository;
import com.proyecto.demo.repository.DestinoRepository;
import com.proyecto.demo.repository.RestauranteRepository;

@Controller
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private DestinoRepository destinoRepository;
	
	@Autowired
	private ComentariosRepository comentariosRepository;
	
	@PostMapping("/agregar")
	public String registrarRestaurante(@ModelAttribute("restaurante") RestauranteDTO restauranteDTO, Model model) {
		
		if (restauranteRepository.findByNombre(restauranteDTO.getNombre()) != null) {
			model.addAttribute("error", "El nombre " + restauranteDTO.getNombre() + " ya se encuentra en uso");
			model.addAttribute("destinos", destinoRepository.findAll());
			return "Restaurante/agregar";
		}
		
		Restaurante restaurante = new Restaurante();
		restaurante.setNombre(restauranteDTO.getNombre());
		restaurante.setMiniDescripcion(restauranteDTO.getMiniDescripcion());
		restaurante.setDescripcion(restauranteDTO.getDescripcion());
		restaurante.setDireccion(restauranteDTO.getDireccion());
		restaurante.setMaps(restauranteDTO.getMaps());
		restaurante.setTelefono(restauranteDTO.getTelefono());
		restaurante.setCorreo(restauranteDTO.getCorreo());
		
		List<byte[]> imagenesBytes = new ArrayList<>();
		for (MultipartFile archivo : restauranteDTO.getImagenes()) {
			try {
				imagenesBytes.add(archivo.getBytes());
			} catch (IOException e) {
				model.addAttribute("error", "Error al procesar las imágenes.");
				return "Restaurante/agregar";
			}
		}
		
		restaurante.setImagenes(imagenesBytes);
		
		Destino destino = destinoRepository.findById(restauranteDTO.getDestino()).orElse(null);
		
		if (destino != null) {
			restaurante.setDestino(destino);
		} else {
			model.addAttribute("error", "Destino no válido");
			return "Restaurante/agregar";
		}
		restauranteRepository.save(restaurante);
		return "redirect:/admin/restaurantes";
	}
	
	@GetMapping("/{id}")
	public String verRestaurantes(@PathVariable("id") String id, Model model) {
		Optional<Destino> destinos = destinoRepository.findById(id);
		
		if (destinos.isPresent()) {
			Destino destino = destinos.get();
			List<Restaurante> restaurantes = restauranteRepository.findByDestino(destino);
			
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
			model.addAttribute("restaurantes", restaurantes);
			model.addAttribute("destino", destino);
		}
		
		return "Restaurante/restaurantes";
	}
	
	@GetMapping("/detalles/{id}")
	public String verRestaurante(@PathVariable("id") String id, Model model) {
		Restaurante restaurantes = restauranteRepository.findById(id).orElse(null);
		
		if (restaurantes != null && restaurantes.getImagenes() != null && !restaurantes.getImagenes().isEmpty()) {
			List<String> imagenesBase64 = new ArrayList<>();
			for (byte[] imagen : restaurantes.getImagenes()) {
				String imagenBase64 = Base64.getEncoder().encodeToString(imagen);
				imagenesBase64.add(imagenBase64);
			}
			restaurantes.setImagenBase64(imagenesBase64);
		}
		
		List<Comentarios> comment = comentariosRepository.findByRestaurante(restaurantes);
		
		for (Comentarios comentarios : comment) {
			Usuario usuario = comentarios.getUsuario();
			Admin admin = comentarios.getAdmin();
			
			if (usuario == null && admin != null) {
				comentarios.setName(admin.getUser());
			} else if (usuario != null) {
				comentarios.setName(usuario.getUser());
			} else {
				comentarios.setName("Desconocido");
			}
		}
		
		model.addAttribute("restaurantes", restaurantes);
		model.addAttribute("comment", comment);
		model.addAttribute("comentarios", new Comentarios());
		return "Restaurante/detalles";
	}
	
	@PostMapping("/editar")
	public String editar(@RequestParam("id") String id, @ModelAttribute RestauranteDTO restauranteDTO, Model model) {
		Restaurante restaurante = restauranteRepository.findById(id).orElse(null);
		
		if (restaurante == null) {
			model.addAttribute("error", "No se ha encontrado el restaurante");
			return "Admin/restaurantes";
		}
		
		restaurante.setNombre(restauranteDTO.getNombre());
		restaurante.setMiniDescripcion(restauranteDTO.getMiniDescripcion());
		restaurante.setDescripcion(restauranteDTO.getDescripcion());
		restaurante.setDireccion(restauranteDTO.getDireccion());
		restaurante.setMaps(restauranteDTO.getMaps());
		restaurante.setTelefono(restauranteDTO.getTelefono());
		restaurante.setCorreo(restauranteDTO.getCorreo());
		
		List<byte[]> imagenesBytes = new ArrayList<>();
		for (MultipartFile archivo : restauranteDTO.getImagenes()) {
			try {
				imagenesBytes.add(archivo.getBytes());
			} catch (IOException e) {
				model.addAttribute("error", "Error al procesar las imágenes.");
				return "Restaurante/agregar";
			}
		}
		
		restaurante.setImagenes(imagenesBytes);
		
		Destino destino = destinoRepository.findById(restauranteDTO.getDestino()).orElse(null);
		
		if (destino != null) {
			restaurante.setDestino(destino);
		} else {
			model.addAttribute("error", "Destino no válido");
			return "Restaurante/editar";
		}
		restauranteRepository.save(restaurante);
		return "redirect:/admin/restaurante/" + id;
	}
}
