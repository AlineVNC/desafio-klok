package br.com.alinevieira.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alinevieira.dtos.ItemDto;
import br.com.alinevieira.dtos.VendaDto;
import br.com.alinevieira.dtos.VendaResponseDto;
import br.com.alinevieira.model.ItemModel;
import br.com.alinevieira.model.ProdutoModel;
import br.com.alinevieira.model.VendaModel;
import br.com.alinevieira.repository.ItemRepository;
import br.com.alinevieira.repository.ProdutoRepository;
import br.com.alinevieira.repository.VendaRepository;
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
		VendaModel venda = new VendaModel();
		venda.setCpfComprador(vendaDto.cpfComprador());
		venda = vendaRepository.save(venda);
		
		if (vendaDto.items() != null) {
			for (ItemDto itemDto : vendaDto.items()) {		
				
				UUID idProduto = itemDto.produto_id();
				Optional<ProdutoModel> optProdutoModel = produtoRepository.findById(idProduto);
				
				if (optProdutoModel.isPresent()) {
					ItemModel itemModel = new ItemModel();
					
					ProdutoModel produtoModel = optProdutoModel.get();					
					itemModel.setProduto(produtoModel);
					
					BigDecimal preco = produtoModel.getPreco();
					itemModel.setPrecoPraticado(preco);
					
					int quantidade = itemDto.quantidade();
					itemModel.setQuantidade(quantidade);
					
					itemModel.setVenda(venda);
					
					itemModel = itemRepository.save(itemModel);
					
					venda.getItems().add(itemModel);					
				}
				
			}
		}
		
		VendaResponseDto vendaResponse = VendaResponseDto.fromModel(venda);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(vendaResponse);
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
