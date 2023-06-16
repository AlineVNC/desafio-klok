package br.com.alinevieira.dtos;

import java.util.UUID;

public record PagamentoResponseTO(
		UUID pagamentoId,
		boolean sucesso,
		String razao
		) {
	
}
