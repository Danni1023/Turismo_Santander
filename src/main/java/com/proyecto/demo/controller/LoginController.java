package com.proyecto.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.demo.models.Admin;
import com.proyecto.demo.models.Usuario;
import com.proyecto.demo.repository.AdminRepository;
import com.proyecto.demo.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/login")
	public String indexLogin(Model model) {
		model.addAttribute("mensaje", false);
		return "login";
	}
	
	@PostMapping("/login")
	public  String login(@RequestParam("user") String user, @RequestParam("password") String password, HttpServletRequest request, Model model) {
		
		Optional<Usuario> usuarioExt = usuarioRepository.findByUserAndPassword(user, password);
		Optional<Admin> administrador = adminRepository.findByUserAndPassword(user, password);
		
		if (usuarioExt.isPresent()) {
			Usuario usuario = usuarioExt.get();
			if (usuario.isActivo()) {
				request.getSession().setAttribute("UserLog", usuario);
				return "redirect:/usuario/";
			} else {
				model.addAttribute("error", "El usuario no se encuentra activo");
				return "login";
			}
		} else if (administrador.isPresent()) {
			Admin admin = administrador.get();
			if (admin.isActivo()) {
				request.getSession().setAttribute("AdminLog", admin);
				return "redirect:/admin/";
			} else {
				model.addAttribute("error", "El usuario no se encuentra activo");
				return "login";
			}
		} else {
			model.addAttribute("error", "Credenciales incorrectas");
			return "login";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/";
	}
}
