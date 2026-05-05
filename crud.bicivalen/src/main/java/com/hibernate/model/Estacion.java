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

	//@ManyToMany(mappedBy = "estacion", fetch = FetchType.LAZY)
	//Set<Bicicleta> bicicletas = new HashSet<>();

	//public void addBicicleta(Bicicleta b) {
		//bicicletas.add(b);
		//b.getEstaciones().add(this);
	//}

	//public void removeBicicleta(Bicicleta b) {
	//	bicicletas.remove(b);
	//	b.getEstaciones().remove(this);
	//}

	//@OneToMany(mappedBy = "estacionI", cascade = CascadeType.ALL)
	///private Set<Ruta> rutasInicio = new HashSet<>();

	//@OneToMany(mappedBy = "estacionD", cascade = CascadeType.ALL)
	//private Set<Ruta> rutasDestino = new HashSet<>();
}
