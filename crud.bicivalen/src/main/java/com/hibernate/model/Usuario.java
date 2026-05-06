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

	@NotBlank(message = "El nombre no puede estar vacío")
	@Size(max = 100)
	@Column(name = "nombre")
	String nombre;

	@NotBlank(message = "El email no puede estar vacío")
	@Email(message = "El email no es válido")
	@Column(name = "email")
	String email;

	@NotBlank(message = "La contraseña no puede estar vacía")
	@Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
	@Column(name = "contraseña")
	String contraseña;

	@OneToMany(mappedBy = "usuario")
	private Set<Ruta> rutas;

	@ManyToOne
	@JoinColumn(name = "bono_id")
	private Bono bonoActual;

    @Future(message = "La fecha de expiración debe ser futura")
	@Column(name = "bono_expiracion")
	private LocalDate bonoExpiracion;

	@Override
	public String toString() {
		return id + " - " + nombre;
	}
}
