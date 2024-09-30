package com.fatec.Acervo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fatec.Acervo.model.Aluno;

@Repository
public interface AlunoRepository extends CrudRepository<Aluno, String>{
	Optional<Aluno> findById(String cpf);
	
	List<Aluno> findAll();
	
	@Procedure(procedureName = "sp_inserirAluno")
	String inserirAluno(@Param("cpf") String cpf, @Param("nome") String nome, @Param("senha") String senha);
	
	@Procedure(procedureName = "sp_atualizarAluno")
	String atualizarAluno(@Param("cpf") String cpf, @Param("senha") String senha);
}
