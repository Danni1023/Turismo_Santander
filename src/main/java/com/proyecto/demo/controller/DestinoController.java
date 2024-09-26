package com.proyecto.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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

import com.proyecto.demo.DTO.DestinoDTO;
import com.proyecto.demo.models.Actividad;
import com.proyecto.demo.models.Admin;
import com.proyecto.demo.models.Comentarios;
import com.proyecto.demo.models.Destino;
import com.proyecto.demo.models.Usuario;
import com.proyecto.demo.repository.ActividadesRepository;
import com.proyecto.demo.repository.ComentariosRepository;
import com.proyecto.demo.repository.DestinoRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/destino")
public class DestinoController {

	@Autowired
	private DestinoRepository destinoRepository;
	
	@Autowired
	private ActividadesRepository actividadesRepository;
	
	@Autowired
	private ComentariosRepository comentariosRepository;
	
	@PostMapping("/agregar")
	public String agregarDestino(@ModelAttribute("destino") DestinoDTO destinoDTO, Model model) {
		
		if (destinoRepository.findByNombre(destinoDTO.getNombre()) != null) {
			model.addAttribute("error", "El nombre " + destinoDTO.getNombre() + " ya se encuentra en uso");
			return "Destino/agregar";
		}
		
		Destino destino = new Destino();
		destino.setNombre(destinoDTO.getNombre());
		destino.setMiniDescripcion(destinoDTO.getMiniDescripcion());
		destino.setDescripcion(destinoDTO.getDescripcion());
		destino.setUbicacion(destinoDTO.getUbicacion());
		destino.setMaps(destinoDTO.getMaps());
		destino.setFavoritos(false);
		
		List<byte[]> imagenesBytes = new ArrayList<>();
		for (MultipartFile archivo : destinoDTO.getImagenes()) {
			try {
				imagenesBytes.add(archivo.getBytes());
			} catch (IOException e) {
				model.addAttribute("error", "Error al procesar las imágenes.");
				return "Destino/nuevoDestino";
			}
		}
		
		destino.setImagenes(imagenesBytes);
		destinoRepository.save(destino);
		return "redirect:/admin/destino";
	}
	
	@GetMapping("/{id}")
	public String verDestino(@PathVariable("id") String id, Model model, HttpServletRequest request) {
		Destino destino = destinoRepository.findById(id).orElse(null);
		
		Usuario usuario = (Usuario) request.getSession().getAttribute("UserLog");
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		String userId = (usuario != null) ? usuario.getId() : (admin != null ? admin.getId() : null);
		
		if (destino != null && destino.getImagenes() != null && !destino.getImagenes().isEmpty()) {
			List<String> imagenesBase64 = new ArrayList<>();
			for (byte[] imagen : destino.getImagenes()) {
				String imagenBase64 = Base64.getEncoder().encodeToString(imagen);
				imagenesBase64.add(imagenBase64);
			}
			destino.setImagenBase64(imagenesBase64);
			destino.setFavoritos(destino.getFavoritosUsuarios() != null && destino.getFavoritosUsuarios().contains(userId));
		}
		
		List<Actividad> actividades = actividadesRepository.findByDestino(destino);
		
		for (Actividad actividad : actividades) {
			if (actividad.getImagenes() != null && !actividad.getImagenes().isEmpty()) {
				List<String> imagenesBase64 = new ArrayList<>();
				for (byte[] imagen : actividad.getImagenes()) {
					String imagenBase64 = Base64.getEncoder().encodeToString(imagen);
					imagenesBase64.add(imagenBase64);
				}
				actividad.setImagenBase64(imagenesBase64);
			}
		}
		
		List<Comentarios> comment = comentariosRepository.findByDestino(destino);
		
		for (Comentarios comentarios : comment) {
			Usuario user = comentarios.getUsuario();
			Admin administrador = comentarios.getAdmin();
			
			if (user == null && administrador != null) {
				comentarios.setName(administrador.getUser());
			} else if (user != null) {
				comentarios.setName(user.getUser());
			} else {
				comentarios.setName("Desconocido");
			}
		}
		
		model.addAttribute("destinos", destino);
		model.addAttribute("actividades", actividades);
		model.addAttribute("comment", comment);
		model.addAttribute("comentarios", new Comentarios());
		return "Destino/detalles";
	}
	
	@PostMapping("/{id}/favorito")
	public String favorito(@PathVariable("id") String id, HttpServletRequest request) {
		Destino destino = destinoRepository.findById(id).orElse(null);
		
		Usuario usuario = (Usuario) request.getSession().getAttribute("UserLog");
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (destino != null) {
			String userId = (usuario != null) ? usuario.getId() : admin.getId();
			
			if (destino.getFavoritosUsuarios() == null) {
				destino.setFavoritosUsuarios(new ArrayList<>());
			}
			
			if (destino.getFavoritosUsuarios().contains(userId)) {
				destino.getFavoritosUsuarios().remove(userId);
			} else {
				destino.getFavoritosUsuarios().add(userId);
			}
			destinoRepository.save(destino);
		}
		
		if (usuario == null && admin == null) {
			return "redirect:/";
		} else if (admin != null) {
			return "redirect:/admin/";
		} else {
			return "redirect:/usuario/";
		}
	}
	
	@PostMapping("/editar")
	public String editar(@RequestParam("id") String id, @ModelAttribute DestinoDTO destinoDTO, Model model) {
		Destino destino = destinoRepository.findById(id).orElse(null);
		
		if (destino == null) {
			model.addAttribute("error", "No se ha encontrado el destino");
			return "Admin/destino";
		}
		
		destino.setNombre(destinoDTO.getNombre());
		destino.setMiniDescripcion(destinoDTO.getMiniDescripcion());
		destino.setDescripcion(destinoDTO.getDescripcion());
		destino.setUbicacion(destinoDTO.getUbicacion());
		destino.setMaps(destinoDTO.getMaps());
		
		List<byte[]> imagenesBytes = new ArrayList<>();
		for (MultipartFile archivo : destinoDTO.getImagenes()) {
			try {
				imagenesBytes.add(archivo.getBytes());
			} catch (IOException e) {
				model.addAttribute("error", "Error al procesar las imágenes.");
				return "Destino/editar";
			}
		}
		
		destino.setImagenes(imagenesBytes);
		destinoRepository.save(destino);
		return "redirect:/admin/destino/" + id;
	}
}
