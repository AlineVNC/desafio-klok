package br.com.alinevieira.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alinevieira.model.ProdutoModel;
import br.com.alinevieira.repository.ProdutoRepository;

@RestController
@RequestMapping(path = "/api/produtos")
public class ProdutoController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	ProdutoRepository produtoRepository;
	
	public ProdutoController(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	@GetMapping
	public ResponseEntity<List<ProdutoModel>> pegaProduto() {
		log.info("Listando produtos...");
		List<ProdutoModel> listaProduto = produtoRepository.findAll();
		return ResponseEntity.ok(listaProduto);
	}
}
