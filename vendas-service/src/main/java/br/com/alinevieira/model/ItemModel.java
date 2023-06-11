package br.com.alinevieira.model;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_item")
public class ItemModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name="venda_id", nullable=false)
	private VendaModel venda;
	
	@ManyToOne
	@JoinColumn(name="produto_id", nullable=false)
	private ProdutoModel produto;
	private int quantidade;
	private BigDecimal precoPraticado;
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public VendaModel getVenda() {
		return venda;
	}
	
	public void setVenda(VendaModel venda) {
		this.venda = venda;
	}
	
	public ProdutoModel getProduto() {
		return produto;
	}
	
	public void setProduto(ProdutoModel produto) {
		this.produto = produto;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public BigDecimal getPrecoPraticado() {
		return precoPraticado;
	}
	
	public void setPrecoPraticado(BigDecimal precoPraticado) {
		this.precoPraticado = precoPraticado;
	}
	
	public BigDecimal getSubTotal() {
		return this.precoPraticado.multiply(new BigDecimal(this.quantidade));
	}
}
