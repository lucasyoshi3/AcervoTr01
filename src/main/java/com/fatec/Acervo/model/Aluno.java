package com.fatec.Acervo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "aluno")
@Getter
@Setter
@NoArgsConstructor
public class Aluno {
	
	@Id
	@Column(name = "cpf",columnDefinition = "CHAR(11)" ,nullable = false)
	private String cpf;
	
	@Column(name = "ra",columnDefinition = "CHAR(11)" ,nullable = false)
	private String ra;
	
	@Column(name = "nome",columnDefinition = "VARCHAR(100)" ,nullable = false)
	private String nome; 
	
	@Column(name = "email",columnDefinition = "VARCHAR(100)" ,nullable = false)
	private String email;
	
	@Column(name = "senha",columnDefinition = "VARCHAR(100)" ,nullable = false)
	private String senha;

}
