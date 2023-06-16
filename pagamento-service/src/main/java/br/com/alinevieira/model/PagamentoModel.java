package br.com.alinevieira.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.alinevieira.model.enums.PagamentoStatus;
import br.com.alinevieira.model.enums.PagamentoTipo;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_pagamento")
public class PagamentoModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private LocalDateTime dataCriacao;
	private LocalDateTime dataFinalizacao;
	private BigDecimal valor;
	
	@Enumerated(EnumType.STRING)
	private PagamentoStatus status;
	
	@Enumerated(EnumType.STRING)
	private PagamentoTipo tipo;
	
	private String cpfPagador;
	
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public LocalDateTime getDataFinalizacao() {
		return dataFinalizacao;
	}
	
	public void setDataFinalizacao(LocalDateTime dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public PagamentoStatus getStatus() {
		return status;
	}
	
	public void setStatus(PagamentoStatus status) {
		this.status = status;
	}
	
	public PagamentoTipo getTipo() {
		return tipo;
	}
	
	public void setTipo(PagamentoTipo tipo) {
		this.tipo = tipo;
	}
	
	public String getCpfPagador() {
		return cpfPagador;
	}
	
	public void setCpfPagador(String cpfPagador) {
		this.cpfPagador = cpfPagador;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
