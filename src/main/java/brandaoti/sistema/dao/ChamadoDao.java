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

	@Query(" select p from Chamado p where p.ativo = 1 and p.atendente is NULL and p.status.aberto = TRUE and p.previsaoFechamento >= now()")
	List<Chamado> buscarAbertos();
	
	@Query(" select p from Chamado p where p.ativo = 1 and p.atendente is not NULL and p.status.encerrado = FALSE and p.previsaoFechamento >= now()")
	List<Chamado> buscarAndamentos();
	
	@Query(" select p from Chamado p where p.ativo = 1 and p.status.encerrado = FALSE and p.previsaoFechamento <= now()")
	List<Chamado> buscarVencidos();
	
	@Query(" select p from Chamado p where p.ativo = 1 and p.status.encerrado = TRUE")
	List<Chamado> buscarEncerrados();
}





