package br.com.alinevieira.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alinevieira.model.ProdutoModel;
import br.com.alinevieira.repository.ProdutoRepository;

@RestController
public class ProdutoController {
	
	@Autowired
	ProdutoRepository produtoRepository;

	@GetMapping("/produtos")
	public ResponseEntity<List<ProdutoModel>> pegaProduto() {
		List<ProdutoModel> listaProduto = produtoRepository.findAll();
		return ResponseEntity.ok(listaProduto);
	}
}
