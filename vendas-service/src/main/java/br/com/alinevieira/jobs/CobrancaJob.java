package br.com.alinevieira.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import br.com.alinevieira.services.CobrancaService;


@Configuration
@EnableScheduling
public class CobrancaJob {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final CobrancaService cobrancaService;
	
	public CobrancaJob(CobrancaService cobrancaService) {
		this.cobrancaService = cobrancaService;
	}



	@Scheduled(cron = "${cobranca.cron}")
	public void gerarCobrancas() {
		log.info("Está rodando a cada 1s.");
		cobrancaService.gerarCobranca();
	}
}
