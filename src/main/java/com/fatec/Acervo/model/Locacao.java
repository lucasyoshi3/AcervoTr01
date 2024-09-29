package com.fatec.Acervo.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "locacao")
public class Locacao {

	@Id
	@Column(name = "codigo",columnDefinition = "INT" ,nullable = false)
	private int codigo;
	
	@Column(name = "dataRetirada",columnDefinition = "DATE" ,nullable = false)
	private LocalDate dataRetirada;
	
	@Column(name = "qtdDiasAluguel",columnDefinition = "INT" ,nullable = false)
	private int qtdDiasAluguel;
	
	@ManyToOne(targetEntity = Exemplar.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "exemplarCodigo", nullable = false)
	private Exemplar exemplar;
	
	@ManyToOne(targetEntity = Aluno.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "alunoCpf")
	private Aluno aluno;
}
