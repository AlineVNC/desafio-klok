package br.com.alinevieira.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.alinevieira.model.PagamentoModel;
import br.com.alinevieira.model.enums.PagamentoStatus;
import br.com.alinevieira.model.enums.PagamentoTipo;

public record PagamentoResponseDto( 
		UUID id,
		UUID vendaId,
		LocalDateTime dataCriacao,
		LocalDateTime dataFinalizacao,
		BigDecimal valor,
		PagamentoStatus status,
		PagamentoTipo tipo,
		String cpfPagador,
		String rejeitadoPor
		) {

	public static PagamentoResponseDto fromModel(PagamentoModel pagamentoModel) {
		return new PagamentoResponseDto(
				pagamentoModel.getId(),
				pagamentoModel.getVendaId(),
				pagamentoModel.getDataCriacao(),
				pagamentoModel.getDataFinalizacao(),
				pagamentoModel.getValor(),
				pagamentoModel.getStatus(),
				pagamentoModel.getTipo(),
				pagamentoModel.getCpfPagador(),
				pagamentoModel.getRejeitadoPor()
				);
	}
}
