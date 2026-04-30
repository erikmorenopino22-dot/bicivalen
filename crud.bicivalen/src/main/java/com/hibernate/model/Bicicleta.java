package com.hibernate.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "bicicleta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bicicleta {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;

	@Column(name = "tipo")
	String tipo;

	@Column(name = "estado")
	String estado;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "control", joinColumns = @JoinColumn(name = "bicicleta_id"), inverseJoinColumns = @JoinColumn(name = "estacion_id"))
	Set<Estacion> estaciones = new HashSet<>();

	public void addEstacion(Estacion e) {
	    estaciones.add(e);
	    e.getBicicletas().add(this);
	}

	public void removeEstacion(Estacion e) {
	    estaciones.remove(e);
	    e.getBicicletas().remove(this);
	}
	

}
