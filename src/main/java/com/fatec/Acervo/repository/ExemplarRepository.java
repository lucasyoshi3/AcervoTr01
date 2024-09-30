package com.fatec.Acervo.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fatec.Acervo.model.Aluno;
import com.fatec.Acervo.model.Exemplar;

@Repository
public interface ExemplarRepository extends CrudRepository<Exemplar, String>{
	
	@Procedure(procedureName = "sp_verificarExemplar")
	List<Exemplar> findByCodigo(@Param("codigo") String codigo);
	
	@Procedure(procedureName = "sp_inserirExemplar")
	String inserirExemplar(@Param("codigo") String codigo, @Param("nome") String nome, @Param("qtdPaginas") int qtdPaginas);
	
	@Procedure(procedureName = "sp_atualizarNomeExemplar")
	void atualizarNomeExemplar(@Param("codigo") String codigo, @Param("nome") String nome);
	
	@Procedure(procedureName = "sp_atualizarQtdPaginasExemplar")
	void atualizarQtdPaginasExemplar(@Param("codigo") String codigo, @Param("qtdPaginas") int qtdPaginas);
	
}
