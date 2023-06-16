package br.com.alinevieira.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.alinevieira.model.PagamentoModel;

public record PagamentoMessageTO(
		UUID pagamentoId,
		UUID vendaId,
		BigDecimal valor
		) {

	public static PagamentoMessageTO fromModel(PagamentoModel pagamentoModel) {
		return new PagamentoMessageTO(
				pagamentoModel.getId(),
				pagamentoModel.getVendaId(),
				pagamentoModel.getValor()
				);
	}
}
