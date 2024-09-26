package com.proyecto.demo.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document(collection="Actividades")
public class Actividad {
	
	@Id
	private String id;
	private String nombre;
	private String descripcion;
	private String ubicacion;
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
