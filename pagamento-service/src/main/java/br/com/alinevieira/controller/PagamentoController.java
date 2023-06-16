package br.com.alinevieira.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alinevieira.dtos.PagamentoDto;
import br.com.alinevieira.dtos.PagamentoResponseDto;
import br.com.alinevieira.model.PagamentoModel;
import br.com.alinevieira.model.enums.PagamentoStatus;
import br.com.alinevieira.repository.PagamentoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/pagamentos")
public class PagamentoController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	PagamentoRepository pagamentoRepository;
	
	public PagamentoController(PagamentoRepository pagamentoRepository) {
		this.pagamentoRepository = pagamentoRepository;
	}
	
	@GetMapping
	public ResponseEntity<List<PagamentoResponseDto>> listarPagamentos() {
		List<PagamentoModel> pagamentos = pagamentoRepository.findAll();
		List<PagamentoResponseDto> pagamentosResponse = new ArrayList<>();
		for(PagamentoModel pagamento : pagamentos) {
			PagamentoResponseDto pagamentoResponse = PagamentoResponseDto.fromModel(pagamento);
			pagamentosResponse.add(pagamentoResponse);
		}
		
		return ResponseEntity.ok(pagamentosResponse);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PagamentoResponseDto> pegarPagamentoPorId(@PathVariable UUID id) {
		Optional<PagamentoModel> optPagamento = pagamentoRepository.findById(id);
		if(optPagamento.isPresent()) {
			PagamentoModel pagamento = optPagamento.get();
			PagamentoResponseDto pagamentoResponse = PagamentoResponseDto.fromModel(pagamento);
			
			return ResponseEntity.ok(pagamentoResponse);
			
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<PagamentoResponseDto> lancaPagamento(@RequestBody @Valid PagamentoDto pagamentoDto) {
		PagamentoModel pagamento = new PagamentoModel();
		pagamento.setCpfPagador(pagamentoDto.cpfPagador());
		pagamento.setDataCriacao(LocalDateTime.now());
		pagamento.setStatus(PagamentoStatus.LANCADO);
		pagamento.setTipo(pagamentoDto.tipo());
		pagamento.setValor(pagamentoDto.valor());
		
		pagamento = pagamentoRepository.save(pagamento);
		
		PagamentoResponseDto pagamentoResponse = PagamentoResponseDto.fromModel(pagamento);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoResponse);
	}
}
