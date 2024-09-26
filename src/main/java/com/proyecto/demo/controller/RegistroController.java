package com.proyecto.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.demo.models.Usuario;
import com.proyecto.demo.repository.UsuarioRepository;

@Controller
@RequestMapping("/registro")
public class RegistroController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("/nuevoUsuario")
    public String nuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "agregar";
	}
	
	@PostMapping("/agregarUsuario")
	public String agregarUsuario(@ModelAttribute("usuario") Usuario usuario, Model model) {
		if (usuarioRepository.findByDocumento(usuario.getDocumento()) != null) {
			model.addAttribute("error", "El número de documento " + usuario.getDocumento() + " ya se encuentra registrado");
			return "agregar";
		}
		
		if (usuario.getId().isEmpty()) {
            usuario.setId(null);
        }
		usuario.setActivo(true);
        usuarioRepository.save(usuario);
        return "redirect:/login";
    }
}
