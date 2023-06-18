package br.com.alinevieira.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import br.com.alinevieira.dtos.CobrancaResponseDto;
import br.com.alinevieira.dtos.ItemDto;
import br.com.alinevieira.dtos.QuantidadeItemDto;
import br.com.alinevieira.dtos.VendaDto;
import br.com.alinevieira.dtos.VendaResponseDto;
import br.com.alinevieira.model.CobrancaModel;
import br.com.alinevieira.model.VendaModel;
import br.com.alinevieira.repository.CobrancaRepository;
import br.com.alinevieira.repository.ItemRepository;
import br.com.alinevieira.repository.ProdutoRepository;
import br.com.alinevieira.repository.VendaRepository;
import br.com.alinevieira.services.VendaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/vendas")
public class VendaController {	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	VendaRepository vendaRepository;
	ProdutoRepository produtoRepository;
	ItemRepository itemRepository;
	CobrancaRepository cobrancaRepository;
	VendaService vendaService;
	
	public VendaController(
			VendaRepository vendaRepository, 
			ProdutoRepository produtoRepository,
			ItemRepository itemRepository, 
			CobrancaRepository cobrancaRepository, 
			VendaService vendaService
			) {
		this.vendaRepository = vendaRepository;
		this.produtoRepository = produtoRepository;
		this.itemRepository = itemRepository;
		this.cobrancaRepository = cobrancaRepository;
		this.vendaService = vendaService;
	}

	@GetMapping
	public ResponseEntity<List<VendaResponseDto>> listarVendas() {
		log.info("Listando vendas...");
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
		log.info("Consultando venda de Id: " + id);
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
	
	@GetMapping("/{id}/cobrancas")
	public ResponseEntity<List<CobrancaResponseDto>> pegarCobrancas(@PathVariable(name = "id") UUID idVenda) {
		log.info("Consultando histórico de cobranças da venda de Id: " + idVenda);
		
		if (!vendaRepository.existsById(idVenda)) {
			return ResponseEntity.notFound().build();
		}
		
		List<CobrancaModel> cobrancas = cobrancaRepository.getAllByVendaId(idVenda);
		List<CobrancaResponseDto> cobrancasResponse = new ArrayList<>();
		for (CobrancaModel cobranca : cobrancas) {
			CobrancaResponseDto cobrancaResponseDto = CobrancaResponseDto.fromModel(cobranca);
			cobrancasResponse.add(cobrancaResponseDto);
			
		}
		return ResponseEntity.ok(cobrancasResponse);
	}
	
	
	@PostMapping
	public ResponseEntity<VendaResponseDto> criaVenda(@RequestBody @Valid VendaDto vendaDto) {
		VendaModel venda = vendaService.criaVenda(vendaDto);
		
		VendaResponseDto vendaResponse = VendaResponseDto.fromModel(venda);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(vendaResponse);
	}
	
	@PostMapping("/{id}/itens")
	public ResponseEntity<VendaResponseDto> adicionarItem(@PathVariable(name = "id") UUID idVenda, @RequestBody @Valid ItemDto itemDto) {
		VendaModel venda = vendaService.adicionarItem(idVenda, itemDto);
		
		VendaResponseDto vendaResponse = VendaResponseDto.fromModel(venda);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(vendaResponse);
		
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
		log.info("Deletando venda de id: " + id);
		boolean vendaExiste = vendaRepository.existsById(id);
		if(vendaExiste) {
			vendaRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}	
	}
}
