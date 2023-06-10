package br.com.alinevieira.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alinevieira.model.CobrancaModel;

@Repository
public interface CobrancaRepository extends JpaRepository<CobrancaModel, UUID> {

}
