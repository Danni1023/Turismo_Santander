package com.proyecto.demo.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.demo.models.Admin;
import com.proyecto.demo.models.Comentarios;
import com.proyecto.demo.models.Destino;
import com.proyecto.demo.models.Hotel;
import com.proyecto.demo.models.Restaurante;
import com.proyecto.demo.models.Usuario;
import com.proyecto.demo.repository.ComentariosRepository;
import com.proyecto.demo.repository.DestinoRepository;
import com.proyecto.demo.repository.HotelRepository;
import com.proyecto.demo.repository.RestauranteRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/comentarios")
public class ComentariosController {

	@Autowired
	private ComentariosRepository comentariosRepository;
	
	@Autowired
	private DestinoRepository destinoRepository;
	
	@Autowired
	private HotelRepository hotelRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@PostMapping("/agregar")
	public String agregar(@ModelAttribute("comentarios") Comentarios comentarios, @RequestParam("destinoId") String destinoId, HttpServletRequest request) {
		
		if (comentarios.getId().isEmpty()) {
            comentarios.setId(null);
        }
		
		Usuario usuario = (Usuario) request.getSession().getAttribute("UserLog");
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (usuario != null) {
			comentarios.setUsuario(usuario);
		} else if (admin != null) {
			comentarios.setAdmin(admin);
		}
		
		Destino destino = destinoRepository.findById(destinoId).orElse(null);
		if (destino != null) {
			comentarios.setDestino(destino);
		}
		
		comentarios.setFechaComentario(LocalDate.now());
        comentariosRepository.save(comentarios);
        
        return "redirect:/destino/" + destinoId;
	}

	@PostMapping("/hotel")
	public String hotel(@ModelAttribute("comentarios") Comentarios comentarios, @RequestParam("hotelId") String hotelId, HttpServletRequest request) {
		
		if (comentarios.getId().isEmpty()) {
            comentarios.setId(null);
        }
		
		Usuario usuario = (Usuario) request.getSession().getAttribute("UserLog");
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (usuario != null) {
			comentarios.setUsuario(usuario);
		} else if (admin != null) {
			comentarios.setAdmin(admin);
		}
		
		Hotel hotel = hotelRepository.findById(hotelId).orElse(null);
		if (hotel != null) {
			comentarios.setHotel(hotel);
		}
		
		comentarios.setFechaComentario(LocalDate.now());
        comentariosRepository.save(comentarios);
        
        return "redirect:/hoteles/detalles/" + hotelId;
	}
	
	@PostMapping("/restaurante")
	public String restaurante(@ModelAttribute("comentarios") Comentarios comentarios, @RequestParam("restauranteId") String restauranteId, HttpServletRequest request) {
		
		if (comentarios.getId().isEmpty()) {
            comentarios.setId(null);
        }
		
		Usuario usuario = (Usuario) request.getSession().getAttribute("UserLog");
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (usuario != null) {
			comentarios.setUsuario(usuario);
		} else if (admin != null) {
			comentarios.setAdmin(admin);
		}
		
		Restaurante restaurante = restauranteRepository.findById(restauranteId).orElse(null);
		if (restaurante != null) {
			comentarios.setRestaurante(restaurante);
		}
		
		comentarios.setFechaComentario(LocalDate.now());
        comentariosRepository.save(comentarios);
        
        return "redirect:/restaurantes/detalles/" + restauranteId;
	}
}
