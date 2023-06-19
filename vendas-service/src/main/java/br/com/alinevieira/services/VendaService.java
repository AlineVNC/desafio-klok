package br.com.alinevieira.services;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.alinevieira.dtos.ItemDto;
import br.com.alinevieira.dtos.QuantidadeItemDto;
import br.com.alinevieira.dtos.VendaDto;
import br.com.alinevieira.exception.BadRequestException;
import br.com.alinevieira.exception.NotFoundException;
import br.com.alinevieira.model.ItemModel;
import br.com.alinevieira.model.ProdutoModel;
import br.com.alinevieira.model.VendaModel;
import br.com.alinevieira.model.enums.VendaStatus;
import br.com.alinevieira.repository.ItemRepository;
import br.com.alinevieira.repository.ProdutoRepository;
import br.com.alinevieira.repository.VendaRepository;
import jakarta.transaction.Transactional;

@Service
public class VendaService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	VendaRepository vendaRepository;
	ProdutoRepository produtoRepository;
	ItemRepository itemRepository;
	
	public VendaService(
			VendaRepository vendaRepository, 
			ProdutoRepository produtoRepository,
			ItemRepository itemRepository
			) {
		this.vendaRepository = vendaRepository;
		this.produtoRepository = produtoRepository;
		this.itemRepository = itemRepository;
	}

	@Transactional
	public VendaModel criaVenda(VendaDto vendaDto) {
		log.info("Cadastrando venda para o comprador de CPF: " + vendaDto.cpfComprador());
		VendaModel venda = new VendaModel();
		venda.setCpfComprador(vendaDto.cpfComprador());
		venda = vendaRepository.save(venda);
		
		if (vendaDto.itens() != null) {
			for (ItemDto itemDto : vendaDto.itens()) {		
				
				UUID idProduto = itemDto.produtoId();
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
					
					venda.getItens().add(itemModel);					
				}
				
			}
		}
		
		return venda;		
	}
	
	@Transactional
	public VendaModel adicionarItem(UUID idVenda, ItemDto itemDto) {
		log.info("Adicionando item a venda de id: " + idVenda + "\nProduto id: " + itemDto.produtoId() + "\nQuantidade: " + itemDto.quantidade());
		Optional<VendaModel> optionalVenda = vendaRepository.findById(idVenda);
		Optional<ProdutoModel> optionalProduto = produtoRepository.findById(itemDto.produtoId());
		if(optionalVenda.isPresent() && optionalProduto.isPresent()) {
			VendaModel venda = optionalVenda.get();
			ProdutoModel produto = optionalProduto.get();
			ItemModel item = new ItemModel();			
			item.setPrecoPraticado(produto.getPreco());
			item.setProduto(produto);
			item.setVenda(venda);
			item.setQuantidade(itemDto.quantidade());
			
			item = itemRepository.save(item);
			
			venda.setStatus(VendaStatus.ALTERADA);
			venda = vendaRepository.save(venda);
			
			return venda;
			
		} else {
			throw new NotFoundException("Venda ou Item não encontrado.");
		}		
	}
	
	@Transactional
	public ItemModel alterarQuantidade(UUID idVenda, UUID idItem, QuantidadeItemDto quantidadeDto) {
		log.info("Venda: " + idVenda + "\nAlterando a quantidade do item de id: " + idItem + " para " + quantidadeDto.quantidade() + ".");
		Optional<ItemModel> optItem = itemRepository.findById(idItem);
		if(optItem.isPresent()) {
			ItemModel item = optItem.get();
			
			if(!item.getVenda().getId().equals(idVenda)) {
				throw new BadRequestException("Item não pertence a esta venda.");
			}		
			
			VendaModel venda  = item.getVenda();
			venda.setStatus(VendaStatus.ALTERADA);
			venda = vendaRepository.save(venda);
			
			item.setQuantidade(quantidadeDto.quantidade());
			item = itemRepository.save(item);
			
			return item;
		} else {
			throw new NotFoundException("Item não encontrado.");
		}		
	}
}
