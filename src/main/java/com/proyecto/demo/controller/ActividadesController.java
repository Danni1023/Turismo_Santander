package com.proyecto.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.demo.DTO.ActividadDTO;
import com.proyecto.demo.models.Actividad;
import com.proyecto.demo.models.Destino;
import com.proyecto.demo.repository.ActividadesRepository;
import com.proyecto.demo.repository.DestinoRepository;

@Controller
@RequestMapping("/actividades")
public class ActividadesController {

	@Autowired
	private ActividadesRepository actividadesRepository;
	
	@Autowired
	private DestinoRepository destinoRepository;
	
	//Método para agregar actividades
	@PostMapping("/agregar")
	public String registrarActividad(@ModelAttribute("actividad") ActividadDTO actividadDTO, Model model) {
		
		Actividad actividad = new Actividad();
		actividad.setNombre(actividadDTO.getNombre());
		actividad.setDescripcion(actividadDTO.getDescripcion());
		actividad.setUbicacion(actividadDTO.getUbicacion());
		
		List<byte[]> imagenesBytes = new ArrayList<>();
		for (MultipartFile archivo : actividadDTO.getImagenes()) {
			try {
				imagenesBytes.add(archivo.getBytes());
			} catch (IOException e) {
				model.addAttribute("error", "Error al procesar las imágenes.");
				return "Actividades/agregar";
			}
		}
		
		actividad.setImagenes(imagenesBytes);
		
		Destino destino = destinoRepository.findById(actividadDTO.getDestino()).orElse(null);
		
		if (destino != null) {
			actividad.setDestino(destino);
		} else {
			model.addAttribute("error", "Destino no válido");
			return "Actividad/agregar";
		}
		actividadesRepository.save(actividad);
		return "redirect:/admin/destino";
	}

	
	//Método para editar actividades
	@PostMapping("/editar")
	public String editar(@RequestParam("id") String id, @ModelAttribute ActividadDTO actividadDTO, Model model) {
		Actividad actividad = actividadesRepository.findById(id).orElse(null);
		
		if (actividad == null) {
			model.addAttribute("error", "No se ha encontrado la actividad");
			return "Admin/hoteles";
		}
		
		actividad.setNombre(actividadDTO.getNombre());
		actividad.setDescripcion(actividadDTO.getDescripcion());
		actividad.setUbicacion(actividadDTO.getUbicacion());
		
		List<byte[]> imagenesBytes = new ArrayList<>();
		for (MultipartFile archivo : actividadDTO.getImagenes()) {
			try {
				imagenesBytes.add(archivo.getBytes());
			} catch (IOException e) {
				model.addAttribute("error", "Error al procesar las imágenes.");
				return "Actividad/agregar";
			}
		}
		
		actividad.setImagenes(imagenesBytes);
		
		Destino destino = destinoRepository.findById(actividadDTO.getDestino()).orElse(null);
		
		if (destino != null) {
			actividad.setDestino(destino);
		} else {
			model.addAttribute("error", "Destino no válido");
			return "Actividades/editar";
		}
		actividadesRepository.save(actividad);
		return "redirect:/admin/destino";
	}
}
