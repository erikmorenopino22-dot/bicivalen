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

	@NotBlank(message = "El tipo no puede estar vacío")
	@Size(max = 50, message = "El tipo no puede superar 50 caracteres")
	@Column(name = "tipo")
	String tipo;

	@NotBlank(message = "El estado no puede estar vacío")
	@Column(name = "estado")
	String estado;

	@NotBlank(message = "El estado de uso no puede estar vacío")
	@Column(name = "estadoUso")
	String estadoUso;

	@ManyToOne
	@JoinColumn(name = "estacion_id")
	private Estacion estacion;

	@Override
	public String toString() {
		return tipo + " (" + id + ")";
	}
}
