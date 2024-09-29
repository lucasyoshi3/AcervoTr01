package com.fatec.Acervo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "exemplar")
public class Exemplar {
	
	@Id
	@Column(name = "codigo",columnDefinition = "VARCHAR(100)" ,nullable = false)
	private String codigo;
	
	@Column(name = "nome",columnDefinition = "VARCHAR(100)" ,nullable = false)
	private String nome;
	
	@Column(name = "qtdPaginas",columnDefinition = "INT" ,nullable = false)
	private int qtdPaginas;
}
