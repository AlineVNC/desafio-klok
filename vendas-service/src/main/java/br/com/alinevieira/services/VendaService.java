package br.com.alinevieira.services;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alinevieira.dtos.ItemDto;
import br.com.alinevieira.dtos.QuantidadeItemDto;
import br.com.alinevieira.dtos.VendaDto;
import br.com.alinevieira.exception.BadRequestException;
import br.com.alinevieira.exception.NotFoundException;
import br.com.alinevieira.model.ItemModel;
import br.com.alinevieira.model.ProdutoModel;
import br.com.alinevieira.model.VendaModel;
import br.com.alinevieira.repository.ItemRepository;
import br.com.alinevieira.repository.ProdutoRepository;
import br.com.alinevieira.repository.VendaRepository;
import jakarta.transaction.Transactional;

@Service
public class VendaService {

	@Autowired
	VendaRepository vendaRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	@Transactional
	public VendaModel criaVenda(VendaDto vendaDto) {
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
		
		return venda;		
	}
	
	@Transactional
	public ItemModel adicionarItem(UUID idVenda, ItemDto itemDto) {
		Optional<VendaModel> optionalVenda = vendaRepository.findById(idVenda);
		Optional<ProdutoModel> optionalProduto = produtoRepository.findById(itemDto.produto_id());
		if(optionalVenda.isPresent() && optionalProduto.isPresent()) {
			VendaModel venda = optionalVenda.get();
			ProdutoModel produto = optionalProduto.get();
			ItemModel item = new ItemModel();
			item.setPrecoPraticado(produto.getPreco());
			item.setProduto(produto);
			item.setVenda(venda);
			item.setQuantidade(itemDto.quantidade());
			
			item = itemRepository.save(item);
			
			return item;
			
		} else {
			throw new NotFoundException("Venda ou Item não encontrado.");
		}		
	}
	
	@Transactional
	public ItemModel alterarQuantidade(UUID idVenda, UUID idItem, QuantidadeItemDto quantidadeDto) {
		Optional<ItemModel> optItem = itemRepository.findById(idItem);
		if(optItem.isPresent()) {
			ItemModel item = optItem.get();
			
			if(!item.getVenda().getId().equals(idVenda)) {
				throw new BadRequestException("Item não pertence a esta venda.");
			}			
			
			item.setQuantidade(quantidadeDto.quantidade());
			item = itemRepository.save(item);
			
			return item;
		} else {
			throw new NotFoundException("Item não encontrado.");
		}		
	}
}
