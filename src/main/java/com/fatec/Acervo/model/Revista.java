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
@Table(name = "revista")
@Getter
@Setter
public class Revista {
	
	@Id
	@ManyToOne(targetEntity = Exemplar.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "ISSN", nullable = false)
	private Exemplar exemplar;
}
