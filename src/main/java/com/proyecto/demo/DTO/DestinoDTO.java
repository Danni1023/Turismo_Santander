package com.proyecto.demo.DTO;

import org.springframework.web.multipart.MultipartFile;

public class DestinoDTO {

	private String id;
	private String nombre;
	private String miniDescripcion;
	private String descripcion;
	private String ubicacion;
	private String maps;
	private MultipartFile[] imagenes;
	
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
	public String getMiniDescripcion() {
		return miniDescripcion;
	}
	public void setMiniDescripcion(String miniDescripcion) {
		this.miniDescripcion = miniDescripcion;
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
	public String getMaps() {
		return maps;
	}
	public void setMaps(String maps) {
		this.maps = maps;
	}
	public MultipartFile[] getImagenes() {
		return imagenes;
	}
	public void setImagenes(MultipartFile[] imagenes) {
		this.imagenes = imagenes;
	}
}
