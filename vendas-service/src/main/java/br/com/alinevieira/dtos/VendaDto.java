package br.com.alinevieira.dtos;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record VendaDto(
		@NotBlank String cpfComprador,
		List<ItemDto> items
		) { }
