package brandaoti.sistema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import brandaoti.sistema.model.Assunto;
import brandaoti.sistema.model.Grupo;
import brandaoti.sistema.model.Perfil;

public interface AssuntoDao extends JpaRepository<Assunto, Integer> {
	
	@Query(" select p from Assunto p where ativo = 1 ")
	List<Assunto> buscarTudo();
	
	@Query(" select p from Assunto p where upper( p.codigo ) like upper( :codigo ) and ativo = 1 ")
	Assunto buscarCodigo(@Param("codigo") String codigo);
	
	@Query(" select p from Assunto p where p.ativo = 1 and upper(p.grupo.nome) like upper( :descricao )")
	List<Assunto> buscarGrupo(@Param("descricao") String descricao);
	
	
}
