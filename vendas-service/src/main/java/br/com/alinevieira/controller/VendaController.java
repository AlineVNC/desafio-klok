package br.com.alinevieira.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class VendaController {

	@Autowired
	VendaRepository vendaRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	public ResponseEntity<List<VendaModel>> listarVendas() {
		List<VendaModel> vendas = vendaRepository.findAll();
		ResponseEntity<List<VendaModel>> response = ResponseEntity.ok(vendas);
		return response; 
	}
	
	@PostMapping("/vendas")
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
}
