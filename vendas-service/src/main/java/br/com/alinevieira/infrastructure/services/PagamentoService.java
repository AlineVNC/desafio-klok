package br.com.alinevieira.infrastructure.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import br.com.alinevieira.dtos.PagamentoRequestTO;
import br.com.alinevieira.infrastructure.configuration.Amqp;
import br.com.alinevieira.services.CobrancaService;

@EnableRabbit
@Service
public class PagamentoService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final CobrancaService cobrancaService;	
			
	public PagamentoService(CobrancaService cobrancaService) {
		this.cobrancaService = cobrancaService;
	}



	@RabbitListener(queues = Amqp.QUEUE_NAME)
    public void receive(final PagamentoRequestTO pagamentoRequest) {
        try {
        	log.info("Recebida a solicitação de pagamento para:\nVenda: " + pagamentoRequest.vendaId() + "\nCobrança: " + pagamentoRequest.cobrancaId());
            
        	cobrancaService.processaPagamento(pagamentoRequest.vendaId(), pagamentoRequest.cobrancaId());            
            
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());                
        }
    }

}
