package com.hibernate.model;

import java.time.LocalDate;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

	@Column(name = "nombre")
	String nombre;

	@Column(name = "tipo")
	String tipo;
	
	@Column(name = "descripcion")
	String descripcion;

}
