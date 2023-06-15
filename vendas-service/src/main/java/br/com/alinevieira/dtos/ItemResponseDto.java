package br.com.alinevieira.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.alinevieira.model.ItemModel;

public record ItemResponseDto(
		UUID id,
		String nomeProduto,
		int quantidade,
		BigDecimal precoPraticado,
		BigDecimal subTotal
		) {
		
	public static ItemResponseDto fromModel(ItemModel itemModel) {
		return new ItemResponseDto(
				itemModel.getId(),
				itemModel.getProduto().getNome(),
				itemModel.getQuantidade(),
				itemModel.getPrecoPraticado(),
				itemModel.getSubTotal()
				);
	}

}
