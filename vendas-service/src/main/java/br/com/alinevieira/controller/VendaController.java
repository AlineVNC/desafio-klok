package br.com.alinevieira.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alinevieira.dtos.ItemDto;
import br.com.alinevieira.dtos.QuantidadeItemDto;
import br.com.alinevieira.dtos.VendaDto;
import br.com.alinevieira.dtos.VendaResponseDto;
import br.com.alinevieira.model.ItemModel;
import br.com.alinevieira.model.VendaModel;
import br.com.alinevieira.repository.ItemRepository;
import br.com.alinevieira.repository.ProdutoRepository;
import br.com.alinevieira.repository.VendaRepository;
import br.com.alinevieira.services.VendaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/vendas")
public class VendaController {

	@Autowired
	VendaRepository vendaRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	VendaService vendaService;
	
	@GetMapping
	public ResponseEntity<List<VendaResponseDto>> listarVendas() {
		List<VendaModel> vendas = vendaRepository.findAll();
		
		List<VendaResponseDto> vendasResponse = new ArrayList<>();
		for (VendaModel venda : vendas) {
			VendaResponseDto vendaResponseDto = VendaResponseDto.fromModel(venda);
			vendasResponse.add(vendaResponseDto);
		}
		
		ResponseEntity<List<VendaResponseDto>> response = ResponseEntity.ok(vendasResponse);
		return response; 
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<VendaResponseDto> pegarVendaPorId(@PathVariable(value="id") UUID id) {
		Optional<VendaModel> optVenda = vendaRepository.findById(id);
		if (optVenda.isPresent()) {
			VendaModel venda = optVenda.get();
			VendaResponseDto vendaResponseDto = VendaResponseDto.fromModel(venda);
			ResponseEntity<VendaResponseDto> response = ResponseEntity.ok(vendaResponseDto);
			return response;
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	@PostMapping
	public ResponseEntity<VendaResponseDto> criaVenda(@RequestBody @Valid VendaDto vendaDto) {
		VendaModel venda = vendaService.criaVenda(vendaDto);
		
		VendaResponseDto vendaResponse = VendaResponseDto.fromModel(venda);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(vendaResponse);
	}
	
	@PostMapping("/{id}/itens")
	public ResponseEntity<Object> adicionarItem(@PathVariable(name = "id") UUID idVenda, @RequestBody @Valid ItemDto itemDto) {
		ItemModel item = vendaService.adicionarItem(idVenda, itemDto);
		
		if(item != null) {			
			return ResponseEntity.status(HttpStatus.CREATED).build();
			
		} else {
			return ResponseEntity.notFound().build();
		}		
	}
	
	@PatchMapping("/{idVenda}/itens/{idItem}")
	public ResponseEntity<Object> alterarQuantidadeItem(
			@PathVariable UUID idVenda, 
			@PathVariable UUID idItem, 
			@RequestBody @Valid QuantidadeItemDto quantidadeDto
			) {
		
		vendaService.alterarQuantidade(idVenda, idItem, quantidadeDto);			
		return ResponseEntity.ok().build();
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletarPorId(@PathVariable UUID id) {
		boolean vendaExiste = vendaRepository.existsById(id);
		if(vendaExiste) {
			vendaRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}	
	}
}
