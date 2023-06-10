package br.com.alinevieira.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.alinevieira.model.enums.CobrancaStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_cobranca")
public class CobrancaModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private LocalDateTime data;
	private BigDecimal valor;
	private CobrancaStatus status;
	private LocalDateTime dataPagamento;
	
	public LocalDateTime getData() {
		return data;
	}
	
	public void setData(LocalDateTime data) {
		this.data = data;
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
	
	public LocalDateTime getDataPagamento() {
		return dataPagamento;
	}
	
	public void setDataPagamento(LocalDateTime dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
}
