package br.com.alinevieira.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.alinevieira.model.ItemModel;
import br.com.alinevieira.model.VendaModel;
import br.com.alinevieira.model.enums.VendaStatus;

public record VendaResponseDto(
		UUID id,
		String cpfComprador,
		VendaStatus status,
		LocalDateTime data,
		List<ItemResponseDto> itens,
		BigDecimal total
		) {
	
	public static VendaResponseDto fromModel(VendaModel vendaModel) {
		List<ItemResponseDto> itensResponseDto = new ArrayList<>();
		for (ItemModel itemModel : vendaModel.getItens()) {
			ItemResponseDto itemResponseDto = ItemResponseDto.fromModel(itemModel);
			itensResponseDto.add(itemResponseDto);
		}
		
		VendaResponseDto vendaResponseDto = new VendaResponseDto(
				vendaModel.getId(),
				vendaModel.getCpfComprador(),
				vendaModel.getStatus(),
				vendaModel.getData(),
				itensResponseDto,
				vendaModel.getTotal()
				);
		
		return vendaResponseDto;
	}

}
