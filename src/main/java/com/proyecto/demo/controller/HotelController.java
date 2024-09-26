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

import com.proyecto.demo.DTO.HotelDTO;
import com.proyecto.demo.models.Admin;
import com.proyecto.demo.models.Comentarios;
import com.proyecto.demo.models.Destino;
import com.proyecto.demo.models.Hotel;
import com.proyecto.demo.models.Usuario;
import com.proyecto.demo.repository.ComentariosRepository;
import com.proyecto.demo.repository.DestinoRepository;
import com.proyecto.demo.repository.HotelRepository;

@Controller
@RequestMapping("/hoteles")
public class HotelController {

	@Autowired
	private HotelRepository hotelRepository;
	
	@Autowired
	private DestinoRepository destinoRepository;
	
	@Autowired
	private ComentariosRepository comentariosRepository;
	
	@PostMapping("/agregar")
	public String registrarHotel(@ModelAttribute("hotel") HotelDTO hotelDTO, Model model) {
		
		if (hotelRepository.findByNombre(hotelDTO.getNombre()) != null) {
			model.addAttribute("error", "El nombre " + hotelDTO.getNombre() + " ya se encuentra en uso");
			model.addAttribute("destinos", destinoRepository.findAll());
			return "Hotel/agregar";
		}
		
		Hotel hotel = new Hotel();
		hotel.setNombre(hotelDTO.getNombre());
		hotel.setMiniDescripcion(hotelDTO.getMiniDescripcion());
		hotel.setDescripcion(hotelDTO.getDescripcion());
		hotel.setDireccion(hotelDTO.getDireccion());
		hotel.setMaps(hotelDTO.getMaps());
		hotel.setTelefono(hotelDTO.getTelefono());
		hotel.setCorreo(hotelDTO.getCorreo());
		
		List<byte[]> imagenesBytes = new ArrayList<>();
		for (MultipartFile archivo : hotelDTO.getImagenes()) {
			try {
				imagenesBytes.add(archivo.getBytes());
			} catch (IOException e) {
				model.addAttribute("error", "Error al procesar las imágenes.");
				return "Hotel/agregar";
			}
		}
		
		hotel.setImagenes(imagenesBytes);
		
		Destino destino = destinoRepository.findById(hotelDTO.getDestino()).orElse(null);
		
		if (destino != null) {
			hotel.setDestino(destino);
		} else {
			model.addAttribute("error", "Destino no válido");
			return "Hotel/agregar";
		}
		hotelRepository.save(hotel);
		return "redirect:/admin/hoteles";
	}
	
	@GetMapping("/{id}")
	public String verHoteles(@PathVariable("id") String id, Model model) {
		Optional<Destino> destinos = destinoRepository.findById(id);
		
		if (destinos.isPresent()) {
			Destino destino = destinos.get();
			List<Hotel> hoteles = hotelRepository.findByDestino(destino);
			
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
			model.addAttribute("hoteles", hoteles);
			model.addAttribute("destino", destino);
		}
		
		return "Hotel/hoteles";
	}
	
	@GetMapping("/detalles/{id}")
	public String verHotel(@PathVariable("id") String id, Model model) {
		Hotel hoteles = hotelRepository.findById(id).orElse(null);
		
		if (hoteles != null && hoteles.getImagenes() != null && !hoteles.getImagenes().isEmpty()) {
			List<String> imagenesBase64 = new ArrayList<>();
			for (byte[] imagen : hoteles.getImagenes()) {
				String imagenBase64 = Base64.getEncoder().encodeToString(imagen);
				imagenesBase64.add(imagenBase64);
			}
			hoteles.setImagenBase64(imagenesBase64);
		}
		
		List<Comentarios> comment = comentariosRepository.findByHotel(hoteles);
		
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
		
		model.addAttribute("hoteles", hoteles);
		model.addAttribute("comment", comment);
		model.addAttribute("comentarios", new Comentarios());
		return "Hotel/detalles";
	}
	
	@PostMapping("/editar")
	public String editar(@RequestParam("id") String id, @ModelAttribute HotelDTO hotelDTO, Model model) {
		Hotel hotel = hotelRepository.findById(id).orElse(null);
		
		if (hotel == null) {
			model.addAttribute("error", "No se ha encontrado el hotel");
			return "Admin/hoteles";
		}
		
		hotel.setNombre(hotelDTO.getNombre());
		hotel.setMiniDescripcion(hotelDTO.getMiniDescripcion());
		hotel.setDescripcion(hotelDTO.getDescripcion());
		hotel.setDireccion(hotelDTO.getDireccion());
		hotel.setMaps(hotelDTO.getMaps());
		hotel.setTelefono(hotelDTO.getTelefono());
		hotel.setCorreo(hotelDTO.getCorreo());
		
		List<byte[]> imagenesBytes = new ArrayList<>();
		for (MultipartFile archivo : hotelDTO.getImagenes()) {
			try {
				imagenesBytes.add(archivo.getBytes());
			} catch (IOException e) {
				model.addAttribute("error", "Error al procesar las imágenes.");
				return "Hotel/agregar";
			}
		}
		
		hotel.setImagenes(imagenesBytes);
		
		Destino destino = destinoRepository.findById(hotelDTO.getDestino()).orElse(null);
		
		if (destino != null) {
			hotel.setDestino(destino);
		} else {
			model.addAttribute("error", "Destino no válido");
			return "Hotel/editar";
		}
		hotelRepository.save(hotel);
		return "redirect:/admin/hotel/" + id;
	}
}
