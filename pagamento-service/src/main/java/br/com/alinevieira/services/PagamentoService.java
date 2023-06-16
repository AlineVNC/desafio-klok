package br.com.alinevieira.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import br.com.alinevieira.dtos.PagamentoDto;
import br.com.alinevieira.dtos.PagamentoMessageResponseTO;
import br.com.alinevieira.dtos.PagamentoMessageTO;
import br.com.alinevieira.infrastructure.configuration.Amqp;
import br.com.alinevieira.model.PagamentoModel;
import br.com.alinevieira.model.enums.PagamentoStatus;
import br.com.alinevieira.repository.PagamentoRepository;
import jakarta.transaction.Transactional;

@EnableRabbit
@Service
public class PagamentoService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final PagamentoRepository pagamentoRepository;
	private final RabbitTemplate rabbitTemplate;	
	
	public PagamentoService(PagamentoRepository pagamentoRepository, RabbitTemplate rabbitTemplate) {
		this.pagamentoRepository = pagamentoRepository;
		this.rabbitTemplate = rabbitTemplate;
	}

	@Transactional
	public PagamentoModel cadastrarPagamento(PagamentoDto pagamentoDto) {
		log.info("Registrando pagamento para venda de id: " + pagamentoDto.vendaId());
		PagamentoModel pagamento = new PagamentoModel();
		pagamento.setVendaId(pagamentoDto.vendaId());
		pagamento.setCpfPagador(pagamentoDto.cpfPagador());
		pagamento.setDataCriacao(LocalDateTime.now());
		pagamento.setStatus(PagamentoStatus.LANCADO);
		pagamento.setTipo(pagamentoDto.tipo());
		pagamento.setValor(pagamentoDto.valor());
		
		pagamento = pagamentoRepository.save(pagamento);
		
		PagamentoMessageTO pagamentoRequest = PagamentoMessageTO.fromModel(pagamento);
		rabbitTemplate.convertAndSend(pagamentoRequest);
		
		return pagamento;
	}
	

	@Transactional
	@RabbitListener(queues = Amqp.FILA_RECEBIMENTO)
    public void receive(PagamentoMessageResponseTO pagamentoResponse) {
		log.info("Processando resposta de pagamendo de id: " + pagamentoResponse.pagamentoId());
        try {
        	Optional<PagamentoModel> optPagamento = pagamentoRepository.findById(pagamentoResponse.pagamentoId());
        	if(optPagamento.isPresent()) {
        		PagamentoModel pagamento = optPagamento.get();
        		if (pagamentoResponse.sucesso()) {
        			pagamento.setStatus(PagamentoStatus.FINALIZADO);
        			pagamentoRepository.save(pagamento);
        		} else {
        			pagamento.setStatus(PagamentoStatus.REJEITADO);
        			pagamento.setRejeitadoPor(pagamentoResponse.razao());
        			pagamentoRepository.save(pagamento);
        		}
        	} else {
        		log.error("Pagamento não encontrado.");
        	}
            
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());                
        }
    }


}
