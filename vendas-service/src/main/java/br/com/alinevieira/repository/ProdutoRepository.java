package br.com.alinevieira.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alinevieira.model.ProdutoModel;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, UUID> {

}
