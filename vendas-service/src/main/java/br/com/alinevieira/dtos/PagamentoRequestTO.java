package br.com.alinevieira.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record PagamentoRequestTO(
		UUID pagamentoId,
		UUID vendaId,
		BigDecimal valor
		) {
	
}
