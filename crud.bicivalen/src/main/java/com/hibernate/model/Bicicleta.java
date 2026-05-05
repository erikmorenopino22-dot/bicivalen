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
	
	@Column(name = "estadoUso")
	String estadoUso; // "LIBRE", "EN_RUTA", "MANTENIMIENTO"
	
	@ManyToOne
	@JoinColumn(name = "estacion_id") 
	private Estacion estacion;
}
