package br.com.alinevieira.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.alinevieira.model.enums.PagamentoTipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PagamentoDto(
		@NotNull UUID vendaId,
		@NotNull BigDecimal valor,
		@NotNull PagamentoTipo tipo,
		@NotBlank String cpfPagador) {

}
