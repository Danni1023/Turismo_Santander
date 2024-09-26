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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.demo.DTO.UserDTO;
import com.proyecto.demo.exception.NotFoundException;
import com.proyecto.demo.models.Actividad;
import com.proyecto.demo.models.Admin;
import com.proyecto.demo.models.Destino;
import com.proyecto.demo.models.Hotel;
import com.proyecto.demo.models.Restaurante;
import com.proyecto.demo.models.Usuario;
import com.proyecto.demo.repository.ActividadesRepository;
import com.proyecto.demo.repository.AdminRepository;
import com.proyecto.demo.repository.ComentariosRepository;
import com.proyecto.demo.repository.DestinoRepository;
import com.proyecto.demo.repository.HotelRepository;
import com.proyecto.demo.repository.RestauranteRepository;
import com.proyecto.demo.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private DestinoRepository destinoRepository;
	
	@Autowired
	private HotelRepository hotelRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private ActividadesRepository actividadesRepository;
	
	@Autowired
	private ComentariosRepository comentariosRepository;
	
	@GetMapping("/")
	public String index(HttpServletRequest request, Model model) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		List<Destino> destinosFavoritos = destinoRepository.findAll().stream()
				.filter(destino -> destino.getFavoritosUsuarios() != null && destino.getFavoritosUsuarios().contains(admin.getId()))
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
			destino.setFavoritos(destino.getFavoritosUsuarios() != null && destino.getFavoritosUsuarios().contains(admin.getId()));
		}
		
		model.addAttribute("destinos", destinosFavoritos);
		model.addAttribute("nombre", admin.getNombre());
		model.addAttribute("apellido", admin.getApellido());
	    return "Admin/index";
	}
	
	@GetMapping("/editarPerfil")
	public String editarPerfil(HttpServletRequest request, Model model) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("admin", admin);
		return "Admin/editarPerfil";
	}
	
	@PostMapping("/actualizar")
	public String actualizarPerfil(@ModelAttribute UserDTO userDTO, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		admin.setTipoDocumento(userDTO.getTipoDocumento());
		admin.setDocumento(userDTO.getDocumento());
		admin.setNombre(userDTO.getNombre());
		admin.setApellido(userDTO.getApellido());
		admin.setCorreo(userDTO.getCorreo());
		admin.setUser(userDTO.getUser());
		
		adminRepository.save(admin);
		return "redirect:/admin/";
	}
	
	@GetMapping("/destino/{id}")
	public String destino(@PathVariable("id") String id, Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		Destino destino = destinoRepository.findById(id).orElse(null);
		
		if (destino != null && destino.getImagenes() != null && !destino.getImagenes().isEmpty()) {
			List<String> imagenesBase64 = new ArrayList<>();
			for (byte[] imagen : destino.getImagenes()) {
				String imagenBase64 = Base64.getEncoder().encodeToString(imagen);
				imagenesBase64.add(imagenBase64);
			}
			destino.setImagenBase64(imagenesBase64);
			destino.setFavoritos(destino.getFavoritosUsuarios() != null && destino.getFavoritosUsuarios().contains(admin.getId()));
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
		
		model.addAttribute("destinos", destino);
		model.addAttribute("actividades", actividades);
		return "Admin/detallesDestino";
	}
	
	@GetMapping("/destino")
	public String destinos(Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
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
			destino.setFavoritos(destino.getFavoritosUsuarios() != null && destino.getFavoritosUsuarios().contains(admin.getId()));
		}
		
		model.addAttribute("destinos", destinos);
	    return "Admin/destino";
	}
	
	@GetMapping("/nuevoDestino")
	public String nuevoDestino(Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("destino", new Destino());
		return "Destino/agregar";
	}
	
	@GetMapping("/editarDestino/{id}")
    public String editarDestino(@PathVariable("id") String id, Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
        Destino destino = destinoRepository.findById(id).orElseThrow(() -> new NotFoundException("El destino no ha sido encontrado"));
        model.addAttribute("destino", destino);
        return "Destino/editar";
    }
	
	@GetMapping("/eliminarDestino/{id}")
    public String eliminarDestino(@PathVariable("id") String id, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
        destinoRepository.deleteById(id);
        hotelRepository.deleteByDestinoId(id);
        restauranteRepository.deleteByDestinoId(id);
        actividadesRepository.deleteByDestinoId(id);
        comentariosRepository.deleteByDestinoId(id);
        return "redirect:/admin/destino";
    }
	
	@GetMapping("/hoteles")
	public String hoteles(Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		List<Hotel> hoteles = hotelRepository.findAll();
		
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
		return "Admin/hoteles";
	}
	
	@GetMapping("/hotel/{id}")
	public String verHotel(@PathVariable("id") String id, Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		Hotel hoteles = hotelRepository.findById(id).orElse(null);
		
		if (hoteles != null && hoteles.getImagenes() != null && !hoteles.getImagenes().isEmpty()) {
			List<String> imagenesBase64 = new ArrayList<>();
			for (byte[] imagen : hoteles.getImagenes()) {
				String imagenBase64 = Base64.getEncoder().encodeToString(imagen);
				imagenesBase64.add(imagenBase64);
			}
			hoteles.setImagenBase64(imagenesBase64);
		}
		
		model.addAttribute("hoteles", hoteles);
		return "Admin/detallesHotel";
	}
	
	@GetMapping("/nuevoHotel")
	public String nuevoHotel(Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("hotel", new Hotel());
		model.addAttribute("destinos", destinoRepository.findAll());
		return "Hotel/agregar";
	}
	
	@GetMapping("/editarHotel/{id}")
	public String editarHotel(@PathVariable("id") String id, Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new NotFoundException("El hotel no ha sido encontrado"));
        
		List<Destino> destinos = destinoRepository.findAll();
		
		model.addAttribute("hotel", hotel);
		model.addAttribute("destinos", destinos);
		return "Hotel/editar";
	}
	
	@GetMapping("/eliminarHotel/{id}")
	public String eliminarHotel(@PathVariable("id") String id, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		hotelRepository.deleteById(id);
		comentariosRepository.deleteByHotelId(id);
		return "redirect:/admin/hoteles";
	}
	
	@GetMapping("/restaurantes")
	public String restaurantes(Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		List<Restaurante> restaurantes = restauranteRepository.findAll();
		
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
		return "Admin/restaurantes";
	}
	
	@GetMapping("/restaurante/{id}")
	public String verRestaurante(@PathVariable("id") String id, Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		Restaurante restaurantes = restauranteRepository.findById(id).orElse(null);
		
		if (restaurantes != null && restaurantes.getImagenes() != null && !restaurantes.getImagenes().isEmpty()) {
			List<String> imagenesBase64 = new ArrayList<>();
			for (byte[] imagen : restaurantes.getImagenes()) {
				String imagenBase64 = Base64.getEncoder().encodeToString(imagen);
				imagenesBase64.add(imagenBase64);
			}
			restaurantes.setImagenBase64(imagenesBase64);
		}
		
		model.addAttribute("restaurantes", restaurantes);
		return "Admin/detallesRestaurante";
	}
	
	@GetMapping("/nuevoRestaurante")
	public String nuevoRestaurante(Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		model.addAttribute("restaurante", new Restaurante());
		model.addAttribute("destinos", destinoRepository.findAll());
		return "Restaurante/agregar";
	}
	
	@GetMapping("/editarRestaurante/{id}")
	public String editarRestaurante(@PathVariable("id") String id, Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		Restaurante restaurante = restauranteRepository.findById(id).orElseThrow(() -> new NotFoundException("El restaurante no ha sido encontrado"));
        
		List<Destino> destinos = destinoRepository.findAll();
		
		model.addAttribute("restaurante", restaurante);
		model.addAttribute("destinos", destinos);
		return "Restaurante/editar";
	}
	
	@GetMapping("/eliminarRestaurante/{id}")
	public String eliminarRestaurante(@PathVariable("id") String id, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		restauranteRepository.deleteById(id);
		comentariosRepository.deleteByRestauranteId(id);
		return "redirect:/admin/restaurantes";
	}
	
	@GetMapping("/nuevasActividades")
	public String nuevasActividades(Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("actividad", new Actividad());
		model.addAttribute("destinos", destinoRepository.findAll());
		return "Actividades/agregar";
	}

	@GetMapping("/editarActividad/{id}")
	public String editarActividad(@PathVariable("id") String id, Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		Actividad actividad = actividadesRepository.findById(id).orElseThrow(() -> new NotFoundException("La actividad no ha sido encontrada"));
        
		List<Destino> destinos = destinoRepository.findAll();
		
		model.addAttribute("actividad", actividad);
		model.addAttribute("destinos", destinos);
		return "Actividades/editar";
	}
	
	@GetMapping("/eliminarActividad/{id}")
	public String eliminarActividad(@PathVariable("id") String id, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		actividadesRepository.deleteById(id);
		return "redirect:/admin/destino";
	}
	
	@GetMapping("/listaAdministradores")
	public String adminList(Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("admin", adminRepository.findAll());
		return "Admin/listar";
	}
	
	@GetMapping("/nuevoAdministrador")
    public String adminNuevo(Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("admin", new Admin());
        return "Admin/agregar";
	}
	
	@PostMapping("/agregar")
	public String adminAgregar(@ModelAttribute("admin") Admin admin, Model model, HttpServletRequest request) {
		Admin adminLog = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (adminLog == null) {
			return "redirect:/login";
		}
		
		if (adminRepository.findByDocumento(admin.getDocumento()) != null) {
			model.addAttribute("error", "El número de documento " + admin.getDocumento() + " ya se encuentra registrado");
			return "Admin/agregar";
		}
		
		if (admin.getId().isEmpty()) {
            admin.setId(null);
        }
		
		admin.setActivo(true);
        adminRepository.save(admin);
        return "redirect:/admin/listaAdministradores";
    }
	
	@GetMapping("/editar/{id}")
    public String adminEditar(@PathVariable("id") String id, Model model, HttpServletRequest request) {
		Admin adminLog = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (adminLog == null) {
			return "redirect:/login";
		}
		
		Admin admin = adminRepository.findById(id).orElseThrow(() -> new NotFoundException("Administrador no ha sido encontrado"));
        model.addAttribute("admin", admin);
        return "Admin/editar";
    }
	
	@PostMapping("/editar")
	public String editar(@RequestParam("id") String id, @ModelAttribute UserDTO userDTO, Model model, HttpServletRequest request) {
		Admin adminLog = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (adminLog == null) {
			return "redirect:/login";
		}
		
		Admin admin = adminRepository.findById(id).orElse(null);
		
		if (admin == null) {
			model.addAttribute("error", "No se ha encontrado el administrador");
			return "Admin/listar";
		}
		
		Admin adminDocument = adminRepository.findByDocumento(userDTO.getDocumento());
		
		if (adminDocument != null && !adminDocument.getId().equals(id)) {
			model.addAttribute("error", "El número de documento ya se encuentra registrado");
			return "Admin/editar";
		}
		
		admin.setTipoDocumento(userDTO.getTipoDocumento());
		admin.setDocumento(userDTO.getDocumento());
		admin.setNombre(userDTO.getNombre());
		admin.setApellido(userDTO.getApellido());
		admin.setCorreo(userDTO.getCorreo());
		admin.setUser(userDTO.getUser());
		
		adminRepository.save(admin);
		return "redirect:/admin/listaAdministradores";
	}
	
	@GetMapping("/eliminar/{id}")
    public String adminEliminar(@PathVariable("id") String id, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		adminRepository.deleteById(id);
		comentariosRepository.deleteByAdminId(id);
        return "redirect:/admin/listaAdministradores";
    }
	
	@GetMapping("/activar/{id}")
	public String activarAdmin(@PathVariable("id") String id, HttpServletRequest request) {
		Admin adminLog = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (adminLog == null) {
			return "redirect:/login";
		}
		
		Admin admin = adminRepository.findById(id).orElseThrow(() -> new NotFoundException("Administrador no ha sido encontrado"));
	    admin.activar();
	    adminRepository.save(admin);
	    return "redirect:/admin/listaAdministradores";
	}

	@GetMapping("/desactivar/{id}")
	public String desactivarAdmin(@PathVariable("id") String id, HttpServletRequest request) {
		Admin adminLog = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (adminLog == null) {
			return "redirect:/login";
		}
		
		Admin admin = adminRepository.findById(id).orElseThrow(() -> new NotFoundException("Administrador no ha sido encontrado"));
	    admin.desactivar();
	    adminRepository.save(admin);
	    return "redirect:/admin/listaAdministradores";
	}
	
	@GetMapping("/listaUsuarios")
	public String userList(Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("usuario", usuarioRepository.findAll());
		return "Usuario/listar";
	}
	
	@GetMapping("/nuevoUsuario")
    public String userNuevo(Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("usuario", new Usuario());
        return "Usuario/agregar";
	}
	
	@PostMapping("/agregarUsuario")
	public String agregarUsuario(@ModelAttribute("usuario") Usuario usuario, Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		if (usuarioRepository.findByDocumento(usuario.getDocumento()) != null) {
			model.addAttribute("error", "El número de documento " + usuario.getDocumento() + " ya se encuentra registrado");
			return "agregar";
		}
		
		if (usuario.getId().isEmpty()) {
            usuario.setId(null);
        }
		usuario.setActivo(true);
        usuarioRepository.save(usuario);
        return "redirect:/admin/listaUsuarios";
    }
	
	@GetMapping("/editarUsuario/{id}")
    public String userEditar(@PathVariable("id") String id, Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuario no ha sido encontrado"));
        model.addAttribute("usuario", usuario);
        return "Usuario/editar";
    }
	
	@PostMapping("/editarUsuario")
	public String editarUsuario(@RequestParam("id") String id, @ModelAttribute UserDTO userDTO, Model model, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		Usuario usuario = usuarioRepository.findById(id).orElse(null);
		
		if (usuario == null) {
			model.addAttribute("error", "No se ha encontrado el usuario");
			return "Usuario/listar";
		}
		
		Usuario usuarioDocument = usuarioRepository.findByDocumento(userDTO.getDocumento());
		
		if (usuarioDocument != null && !usuarioDocument.getId().equals(id)) {
			model.addAttribute("error", "El número de documento ya se encuentra registrado");
			return "Admin/editar";
		}
		
		usuario.setTipoDocumento(userDTO.getTipoDocumento());
		usuario.setDocumento(userDTO.getDocumento());
		usuario.setNombre(userDTO.getNombre());
		usuario.setApellido(userDTO.getApellido());
		usuario.setCorreo(userDTO.getCorreo());
		usuario.setUser(userDTO.getUser());
		
		usuarioRepository.save(usuario);
		return "redirect:/admin/listaUsuarios";
	}
	
	@GetMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable("id") String id, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
        usuarioRepository.deleteById(id);
        comentariosRepository.deleteByUsuarioId(id);
        return "redirect:/admin/listaUsuarios";
    }
	
	@GetMapping("/activarUsuario/{id}")
	public String activarUser(@PathVariable("id") String id, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
	    Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("El usuario no ha sido encontrado"));
	    usuario.activar();
	    usuarioRepository.save(usuario);
	    return "redirect:/admin/listaUsuarios";
	}

	@GetMapping("/desactivarUsuario/{id}")
	public String desactivarUser(@PathVariable("id") String id, HttpServletRequest request) {
		Admin admin = (Admin) request.getSession().getAttribute("AdminLog");
		
		if (admin == null) {
			return "redirect:/login";
		}
		
		Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("El usuario no ha sido encontrado"));
	    usuario.desactivar();
	    usuarioRepository.save(usuario);
	    return "redirect:/admin/listaUsuarios";
	}
}
