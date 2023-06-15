package br.com.alinevieira.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.alinevieira.model.CobrancaModel;
import br.com.alinevieira.model.VendaModel;
import br.com.alinevieira.model.enums.CobrancaStatus;
import br.com.alinevieira.model.enums.VendaStatus;
import br.com.alinevieira.repository.CobrancaRepository;
import br.com.alinevieira.repository.VendaRepository;
import jakarta.transaction.Transactional;

@Service
public class CobrancaService {
	
	private final CobrancaRepository cobrancaRepository;
	private final VendaRepository vendaRepository;

	public CobrancaService(CobrancaRepository cobrancaRepository, VendaRepository vendaRepository) {
		this.cobrancaRepository = cobrancaRepository;
		this.vendaRepository = vendaRepository;
	}
	
	@Transactional
	public void gerarCobranca() {
		List<VendaModel> vendasEmAberto = vendaRepository.getAllByStatusIn(new VendaStatus[]{VendaStatus.ALTERADA, VendaStatus.CRIADA});
		
		for(VendaModel vendaModel : vendasEmAberto) {
			System.out.println(vendaModel.getId());
			List<CobrancaModel> cobrancasEmAberto = cobrancaRepository.getAllByVendaIdAndStatus(vendaModel.getId(), CobrancaStatus.EM_ABERTO);
			
			for(CobrancaModel cobrancaEmAberto : cobrancasEmAberto) {
				cobrancaEmAberto.setStatus(CobrancaStatus.EXPIRADO);
				cobrancaRepository.save(cobrancaEmAberto);
			}
			
			CobrancaModel cobrancaModel = new CobrancaModel();
			cobrancaModel.setDataCriacao(LocalDateTime.now());
			cobrancaModel.setStatus(CobrancaStatus.EM_ABERTO);
			cobrancaModel.setValor(vendaModel.getTotal());
			cobrancaModel.setVenda(vendaModel);
			
			cobrancaRepository.save(cobrancaModel);
			
			vendaModel.setStatus(VendaStatus.COBRANCA_GERADA);
			vendaRepository.save(vendaModel);
		}				
	}	
}
