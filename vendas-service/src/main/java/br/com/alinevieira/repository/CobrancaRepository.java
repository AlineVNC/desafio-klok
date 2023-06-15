package br.com.alinevieira.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alinevieira.model.CobrancaModel;
import br.com.alinevieira.model.enums.CobrancaStatus;

@Repository
public interface CobrancaRepository extends JpaRepository<CobrancaModel, UUID> {
	
	List<CobrancaModel> getAllByVendaIdAndStatus(UUID vendaId, CobrancaStatus status); 
	
	List<CobrancaModel> getAllByVendaId(UUID vendaId);
}
