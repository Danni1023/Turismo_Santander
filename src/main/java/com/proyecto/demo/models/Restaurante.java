package com.proyecto.demo.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document(collection="Restaurantes")
public class Restaurante {
	
	@Id
	private String id;
	private String nombre;
	private String miniDescripcion;
	private String descripcion;
	private String direccion;
	private String maps;
	private String telefono;
	private String correo;
	private List<byte[]> imagenes;
	private List<String> imagenBase64;
	
	@DocumentReference
	private Destino destino;

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

	public List<byte[]> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<byte[]> imagenes) {
		this.imagenes = imagenes;
	}

	public List<String> getImagenBase64() {
		return imagenBase64;
	}

	public void setImagenBase64(List<String> imagenBase64) {
		this.imagenBase64 = imagenBase64;
	}

	public Destino getDestino() {
		return destino;
	}

	public void setDestino(Destino destino) {
		this.destino = destino;
	}

}
