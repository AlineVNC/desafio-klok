package br.com.alinevieira.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alinevieira.model.VendaModel;
import br.com.alinevieira.model.enums.VendaStatus;

@Repository
public interface VendaRepository extends JpaRepository<VendaModel, UUID>{
	
	List<VendaModel> getAllByStatusIn(VendaStatus[] status); 

}
