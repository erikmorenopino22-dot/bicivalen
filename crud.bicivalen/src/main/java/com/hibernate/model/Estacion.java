package com.hibernate.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "estaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estacion {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;

	@NotBlank(message = "El nombre no puede estar vacío")
	@Size(max = 100)
	@Column(name = "nombre")
	String nombre;

	@NotBlank(message = "La ubicación no puede estar vacía")
	@Column(name = "ubicacion")
	String ubicacion;

	@Min(value = 1, message = "La capacidad debe ser mayor que 0")
	@Column(name = "capacidad")
	int capacidad;

	@Min(value = 0, message = "Los puestos no pueden ser negativos")
	@Column(name = "puestos")
	int puestos;
	
	@OneToMany(mappedBy = "estacion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Bicicleta> bicicletas = new HashSet<>();

	public void addBicicleta(Bicicleta b) {
		bicicletas.add(b);
		b.setEstacion(this);
	}

	public void removeBicicleta(Bicicleta b) {
		bicicletas.remove(b);
		b.setEstacion(null);
	}

	@Override
	public String toString() {
		return nombre;
	}
}
