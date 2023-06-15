package br.com.alinevieira.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.alinevieira.model.enums.CobrancaStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_cobranca")
public class CobrancaModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name="venda_id", nullable=false)
	private VendaModel venda;
	private BigDecimal valor;
	
	@Enumerated(EnumType.STRING)
	private CobrancaStatus status;
	private LocalDateTime dataCriacao;
	private LocalDateTime dataPagamento;
	
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

	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public CobrancaStatus getStatus() {
		return status;
	}
	
	public void setStatus(CobrancaStatus status) {
		this.status = status;
	}
	
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public LocalDateTime getDataPagamento() {
		return dataPagamento;
	}
	
	public void setDataPagamento(LocalDateTime dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
}
