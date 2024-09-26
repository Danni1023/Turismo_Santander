package com.proyecto.demo.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Destino")
public class Destino {
	
	@Id
	private String id;
	private String nombre;
	private String miniDescripcion;
	private String descripcion;
	private String ubicacion;
	private String maps;
	private List<byte[]> imagenes;
	private List<String> imagenBase64;
	private List<String> favoritosUsuarios;
	private boolean favoritos;
	
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
	public List<String> getFavoritosUsuarios() {
		return favoritosUsuarios;
	}
	public void setFavoritosUsuarios(List<String> favoritosUsuarios) {
		this.favoritosUsuarios = favoritosUsuarios;
	}
	public boolean isFavoritos() {
		return favoritos;
	}
	public void setFavoritos(boolean favoritos) {
		this.favoritos = favoritos;
	}
	

}
