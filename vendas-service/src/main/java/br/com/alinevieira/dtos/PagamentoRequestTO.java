package br.com.alinevieira.dtos;

import java.util.UUID;

public record PagamentoRequestTO(
		UUID cobrancaId,
		UUID vendaId
		) {}
