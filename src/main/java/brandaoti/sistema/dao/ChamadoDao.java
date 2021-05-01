package brandaoti.sistema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import brandaoti.sistema.model.Chamado;
import brandaoti.sistema.model.Grupo;

public interface ChamadoDao extends JpaRepository<Chamado, Integer> {
	
	@Query(" select p from Chamado p where ativo = 1 ")
	List<Chamado> buscarTudo();

	@Query(" select p from Chamado p where ativo = 1 and p.solicitante.id like (:id) and p.atendente.id like (:id) order by p.dataAbertura desc")
	List<Chamado> buscarMeusChamados(@Param("id") String id);
	
	@Query(" select p from Chamado p where ativo = 1 and p.numeroChamado like :numeroChamado order by p.dataAbertura desc")
	List<Chamado> buscarChamado(@Param("numeroChamado") String numeroChamado);

}
