package br.com.alinevieira.infrastructure.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Amqp {
	public static final String FILA_ENVIO = "vendas.pagamento.entrada";
	public static final String FILA_RECEBIMENTO = "vendas.pagamento.saida";
		
	private final CachingConnectionFactory cachingConnectionFactory;

    public Amqp(CachingConnectionFactory cachingConnectionFactory) {
        this.cachingConnectionFactory = cachingConnectionFactory;
    }
    
	@Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

	@Bean
    public Queue queue_envio() {
        return new Queue(FILA_ENVIO);
    }  
	
    @Bean
    public Queue queue_recebimento() {
        return new Queue(FILA_RECEBIMENTO);
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        template.setRoutingKey(FILA_ENVIO);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

}
