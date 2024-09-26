package com.proyecto.demo.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.demo.DTO.UserDTO;
import com.proyecto.demo.models.Destino;
import com.proyecto.demo.models.Usuario;
import com.proyecto.demo.repository.DestinoRepository;
import com.proyecto.demo.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private DestinoRepository destinoRepository;
	
	@GetMapping("/")
	public String index(HttpServletRequest request, Model model) {
		Usuario usuario = (Usuario) request.getSession().getAttribute("UserLog");
		
		if (usuario == null) {
			return "redirect:/login";
		}
		
		List<Destino> destinosFavoritos = destinoRepository.findAll().stream()
				.filter(destino -> destino.getFavoritosUsuarios() != null && destino.getFavoritosUsuarios().contains(usuario.getId()))
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
			destino.setFavoritos(destino.getFavoritosUsuarios() != null && destino.getFavoritosUsuarios().contains(usuario.getId()));
		}
		
		model.addAttribute("destinos", destinosFavoritos);
		model.addAttribute("nombre", usuario.getNombre());
        model.addAttribute("apellido", usuario.getApellido());
		return "Usuario/index";
	}
	
	@GetMapping("/editarPerfil")
	public String editarPerfil(HttpServletRequest request, Model model) {
		Usuario usuario = (Usuario) request.getSession().getAttribute("UserLog");
		
		if (usuario == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("usuario", usuario);
		return "Usuario/editarPerfil";
	}
	
	@PostMapping("/actualizar")
	public String actualizarPerfil(@ModelAttribute UserDTO userDTO, HttpServletRequest request) {
		Usuario usuario = (Usuario) request.getSession().getAttribute("UserLog");
		
		if (usuario == null) {
			return "redirect:/login";
		}
		
		usuario.setTipoDocumento(userDTO.getTipoDocumento());
		usuario.setDocumento(userDTO.getDocumento());
		usuario.setNombre(userDTO.getNombre());
		usuario.setApellido(userDTO.getApellido());
		usuario.setCorreo(userDTO.getCorreo());
		usuario.setUser(userDTO.getUser());
		
		usuarioRepository.save(usuario);
		return "redirect:/usuario/";
	}
}
