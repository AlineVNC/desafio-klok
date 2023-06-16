package br.com.alinevieira.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import br.com.alinevieira.dtos.PagamentoRequestTO;
import br.com.alinevieira.dtos.PagamentoResponseTO;
import br.com.alinevieira.model.CobrancaModel;
import br.com.alinevieira.model.VendaModel;
import br.com.alinevieira.model.enums.CobrancaStatus;
import br.com.alinevieira.model.enums.VendaStatus;
import br.com.alinevieira.repository.CobrancaRepository;
import br.com.alinevieira.repository.VendaRepository;
import jakarta.transaction.Transactional;

@Service
public class CobrancaService {	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final CobrancaRepository cobrancaRepository;
	private final VendaRepository vendaRepository;
	private final RabbitTemplate rabbitTemplate;

	public CobrancaService(
			CobrancaRepository cobrancaRepository, 
			VendaRepository vendaRepository, 
			RabbitTemplate rabbitTemplate
			) {
		this.cobrancaRepository = cobrancaRepository;
		this.vendaRepository = vendaRepository;
		this.rabbitTemplate = rabbitTemplate;
	}
	
	@Transactional
	public void gerarCobranca() {
		List<VendaModel> vendasEmAberto = vendaRepository.getAllByStatusIn(new VendaStatus[]{VendaStatus.ALTERADA, VendaStatus.CRIADA});
		
		for(VendaModel vendaModel : vendasEmAberto) {
			log.info("Gerando cobrança para venda de Id: " + vendaModel.getId());
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
	
	@Transactional
	public void processaPagamento(PagamentoRequestTO pagamentoRequest) {
		Optional<CobrancaModel> optCobranca = cobrancaRepository.findOneByVendaIdAndStatus(pagamentoRequest.vendaId(), CobrancaStatus.EM_ABERTO);
		
		if(optCobranca.isPresent()) {
			CobrancaModel cobranca = optCobranca.get();
			if(!cobranca.getVenda().getId().equals(pagamentoRequest.vendaId())) {
				log.error("Cobrança não pertence a venda informada. Id " + cobranca);				
			} else if (!pagamentoRequest.valor().equals(cobranca.getValor())) {
				log.error("Valor do pagamento diferente da cobrança. Id " + cobranca);
				
				PagamentoResponseTO response = new PagamentoResponseTO(pagamentoRequest.pagamentoId(), false, "Valores divergentes");
				rabbitTemplate.convertAndSend(response);
			} else {
				VendaModel venda = cobranca.getVenda();
				venda.setStatus(VendaStatus.PAGA);
				vendaRepository.save(venda);
				
				cobranca.setStatus(CobrancaStatus.PAGO);
				cobranca.setDataPagamento(LocalDateTime.now());
				cobrancaRepository.save(cobranca);
				
				log.info("Cobranca: " + cobranca.getId() + "\nPagamento realizado.");
				
				PagamentoResponseTO response = new PagamentoResponseTO(pagamentoRequest.pagamentoId(), true, null);
				rabbitTemplate.convertAndSend(response);				
			}
		} else {
			log.error("Cobrança em aberto não encontrada para a venda de Id: " + pagamentoRequest.vendaId());
			
			PagamentoResponseTO response = new PagamentoResponseTO(pagamentoRequest.pagamentoId(), false, "Cobranças em aberto não encontradas.");
			rabbitTemplate.convertAndSend(response);
		}		
	}
}
