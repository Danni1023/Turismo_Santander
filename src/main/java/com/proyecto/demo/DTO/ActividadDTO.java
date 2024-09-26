package com.proyecto.demo.DTO;

import org.springframework.web.multipart.MultipartFile;

public class ActividadDTO {

	private String id;
	private String nombre;
	private String descripcion;
	private String ubicacion;
	private MultipartFile[] imagenes;
	private String destino;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public MultipartFile[] getImagenes() {
		return imagenes;
	}
	public void setImagenes(MultipartFile[] imagenes) {
		this.imagenes = imagenes;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
}
