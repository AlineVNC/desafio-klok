package br.com.alinevieira.dtos;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemDto(
		@NotNull UUID produto_id,
		@Min(value = 1) int quantidade
		) {

}
