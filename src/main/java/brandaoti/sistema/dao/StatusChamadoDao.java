package brandaoti.sistema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import brandaoti.sistema.model.StatusChamado;

public interface StatusChamadoDao extends JpaRepository<StatusChamado, Integer> {
	
	@Query(" select p from StatusChamado p where p.ativo = TRUE")
	List<StatusChamado> buscarTudo();
	
	@Query(" select p from StatusChamado p where p.ativo = TRUE AND p.aberto = TRUE")
	List<StatusChamado> buscarAberto();
}
