package com.hibernate.model;

import java.time.LocalDate;
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

	@OneToMany(mappedBy = "usuario")
	private Set<Ruta> rutas;
	
	@ManyToOne
	@JoinColumn(name = "bono_id")
	private Bono bonoActual;

	@Column(name = "bono_expiracion")
	private LocalDate bonoExpiracion;
	
	@Override
	public String toString() { return id + " - " + nombre; }
}
