package com.hibernate.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bono")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bono {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;

	@NotBlank(message = "El nombre no puede estar vacío")
	@Size(max = 100)
	@Column(name = "nombre")
	String nombre;

	@NotBlank(message = "El tipo no puede estar vacío")
	@Column(name = "tipo")
	String tipo;

	@Size(max = 255)
	@Column(name = "descripcion")
	String descripcion;

}
