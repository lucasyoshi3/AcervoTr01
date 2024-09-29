package repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fatec.Acervo.model.Aluno;
import com.fatec.Acervo.model.Exemplar;

@Repository
public interface ExemplarRepository extends CrudRepository<Exemplar, String>{
	
	Optional<Exemplar> findById(String codigo);
	
	List<Exemplar> findAll();
	
	@Procedure(procedureName = "sp_inserirExemplar")
	String inserirExemplar(@Param("codigo") String codigo, @Param("nome") String nome, @Param("qtdPaginas") int qtdPaginas);
	
	@Procedure(procedureName = "sp_atualizarNomeExemplar")
	String atualizarNomeExemplar(@Param("codigo") String codigo, @Param("nome") String nome);
	
	@Procedure(procedureName = "sp_atualizarQtdPaginasExemplar")
	void atualizarQtdPaginasExemplar(@Param("codigo") String codigo, @Param("qtdPaginas") int qtdPaginas);
	
}
