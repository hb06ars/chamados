package brandaoti.sistema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import brandaoti.sistema.model.Grupo;
import brandaoti.sistema.model.Perfil;

public interface GrupoDao extends JpaRepository<Grupo, Integer> {
	
	@Query(" select p from Grupo p where ativo = 1 ")
	List<Grupo> buscarTudo();
	
	@Query(" select p from Grupo p where upper( p.codigo ) like upper( :codigo ) and ativo = 1 ")
	Grupo buscarCodigo(@Param("codigo") String codigo);
	
	@Query(" select p from Grupo p where cliente = 1")
	List<Grupo> buscarCliente();
	
	@Query(" select p from Grupo p where gestor = 1")
	List<Grupo> buscarGestor();
	
	@Query(" select p from Grupo p where funcionario = 1")
	List<Grupo> buscarFuncionario();
	
	@Query(" select p from Grupo p where administrador = 1")
	List<Grupo> buscarAdministrador();
	
	
}
