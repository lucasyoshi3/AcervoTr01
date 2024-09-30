package com.fatec.Acervo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "livro")
@Getter
@Setter
public class Livro {
	
	@Id
	@ManyToOne(targetEntity = Exemplar.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "ISBN", nullable = false)
	private Exemplar exemplar;
}
