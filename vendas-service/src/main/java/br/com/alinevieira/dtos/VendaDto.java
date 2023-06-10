package br.com.alinevieira.dtos;

import jakarta.validation.constraints.NotBlank;

public record VendaDto(@NotBlank String cpfComprador) { }
