package com.hibernate.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;

	@Column(name = "nombre")
	String nombre;

	@Column(name = "email")
	String email;

	@Column(name = "contraseña")
	String contraseña;

	//@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	//private Set<Ruta> rutas = new HashSet<>();
}
