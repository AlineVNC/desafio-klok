package br.com.alinevieira.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.alinevieira.dtos.VendaDto;
import br.com.alinevieira.model.VendaModel;
import br.com.alinevieira.repository.VendaRepository;
import jakarta.validation.Valid;

@RestController
public class VendaController {

	@Autowired
	VendaRepository vendaRepository;
	
	public ResponseEntity<List<VendaModel>> listarVendas() {
		List<VendaModel> vendas = vendaRepository.findAll();
		ResponseEntity<List<VendaModel>> response = ResponseEntity.ok(vendas);
		return response; 
	}
	
	@PostMapping("/vendas")
	public ResponseEntity<VendaModel> criaVenda(@RequestBody @Valid VendaDto vendaDto) {
		VendaModel venda = new VendaModel();
		venda.setCpfComprador(vendaDto.cpfComprador());
		venda.setData(LocalDateTime.now());
		
		venda = vendaRepository.save(venda);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(venda);
	}
}
