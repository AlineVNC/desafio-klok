package br.com.alinevieira.dtos;

import java.util.UUID;

public record PagamentoMessageResponseTO(
		UUID pagamentoId, 
		boolean sucesso,
		String razao
		) {
	
}
