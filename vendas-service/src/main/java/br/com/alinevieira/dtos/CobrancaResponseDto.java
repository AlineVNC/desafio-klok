package br.com.alinevieira.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.alinevieira.model.enums.CobrancaStatus;

public record CobrancaResponseDto(
		UUID id,
		BigDecimal valor,
		CobrancaStatus status,
		LocalDateTime dataCriacao,
		LocalDateTime dataPagamento) {

}
