package com.felixpessoa.msavaliadorcredito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.felixpessoa.msavaliadorcredito.model.DadosAvaliacao;
import com.felixpessoa.msavaliadorcredito.model.DadosSolicitacaoEmissaoCartao;
import com.felixpessoa.msavaliadorcredito.model.ProtocoloSolicitacaoCartao;
import com.felixpessoa.msavaliadorcredito.model.RetornoAvaliacaoCliente;
import com.felixpessoa.msavaliadorcredito.model.SituacaoCliente;
import com.felixpessoa.msavaliadorcredito.service.AvaliadorCreditoService;
import com.felixpessoa.msavaliadorcredito.service.exception.DadosClienteNotFoundException;
import com.felixpessoa.msavaliadorcredito.service.exception.ErroComunicacaoMicroservicesException;
import com.felixpessoa.msavaliadorcredito.service.exception.ErroSolicitacaoCartaoException;

@RestController
@RequestMapping("avaliacoes-credito")
public class AvaliadorCreditoController {

	@Autowired
	private AvaliadorCreditoService avaliadorCreditoService;

	@GetMapping
	public String status() {
		return "ok";
	}

	@GetMapping(value = "situacao-cliente", params = "cpf")
	public ResponseEntity<?> consultarSituacaoCliente(@RequestParam("cpf") String cpf) {
		try {
			SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
			return ResponseEntity.ok(situacaoCliente);
		} catch (DadosClienteNotFoundException e) {

			return ResponseEntity.notFound().build();

		} catch (ErroComunicacaoMicroservicesException e) {

			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());

		}

	}
	
	@PostMapping
	public ResponseEntity<?> realizarAvaliacao(@RequestBody DadosAvaliacao dados) {
		try {
			RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService.realizarAvaliacao(dados.getCpf(), dados.getRenda());
			return ResponseEntity.ok(retornoAvaliacaoCliente);
			
		} catch (DadosClienteNotFoundException e) {

			return ResponseEntity.notFound().build();

		} catch (ErroComunicacaoMicroservicesException e) {

			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());

		}
	}
	
	@PostMapping("solicitacoes-cartao")
	public ResponseEntity<?> solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartao dados){
		try {
			ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = avaliadorCreditoService.solicitarEmissaoCartao(dados);
			return ResponseEntity.ok(protocoloSolicitacaoCartao);
		} catch (ErroSolicitacaoCartaoException e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

}
