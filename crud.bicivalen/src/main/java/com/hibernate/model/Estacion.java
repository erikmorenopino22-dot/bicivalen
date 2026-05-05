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

	@Column(name = "nombre")
	String nombre;
	@Column(name = "ubicacion")
	String ubicacion;
	@Column(name = "capacidad")
	int capacidad;
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
	    return nombre; // o nombre + " (" + id + ")"
	}
}
