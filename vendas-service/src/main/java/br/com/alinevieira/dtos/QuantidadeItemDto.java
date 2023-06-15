package br.com.alinevieira.dtos;

import jakarta.validation.constraints.Min;

public record QuantidadeItemDto(
		@Min(value = 1) int quantidade
		) {
}
