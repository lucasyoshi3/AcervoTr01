package com.fatec.Acervo.repository;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fatec.Acervo.model.Aluno;

@Repository
public interface LoginRepository extends CrudRepository<Aluno, String>{
	
	@Procedure(procedureName = "sp_login")
	Boolean login(@Param("email") String email, @Param("senha") String senha);

}
