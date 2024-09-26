package com.proyecto.demo.DTO;

import org.springframework.web.multipart.MultipartFile;

public class RestauranteDTO {

	private String id;
	private String nombre;
	private String miniDescripcion;
	private String descripcion;
	private String direccion;
	private String maps;
	private String telefono;
	private String correo;
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
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getMaps() {
		return maps;
	}
	public void setMaps(String maps) {
		this.maps = maps;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
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
