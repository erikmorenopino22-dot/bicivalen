package com.hibernate.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "ruta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ruta {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "estacionI_id")
	private Estacion estacionI;

	@ManyToOne
	@JoinColumn(name = "estacionD_id")
	private Estacion estacionD;

	@Column(name = "inicio")
	LocalDateTime inicio;
	
	@Column(name = "final")
	LocalDateTime fin;

	@Column(name = "valoracion")
	int valoracion;

}
