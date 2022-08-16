package com.felixpessoa.msavaliadorcredito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.felixpessoa.msavaliadorcredito.model.SituacaoCliente;
import com.felixpessoa.msavaliadorcredito.service.AvaliadorCreditoService;
import com.felixpessoa.msavaliadorcredito.service.exception.DadosClienteNotFoundException;
import com.felixpessoa.msavaliadorcredito.service.exception.ErroComunicacaoMicroservicesException;

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
	public ResponseEntity<?> consultaSituacaoCliente(@RequestParam("cpf") String cpf) {
		try {
			SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
			return ResponseEntity.ok(situacaoCliente);
		} catch (DadosClienteNotFoundException e) {
			
			return ResponseEntity.notFound().build();
			
		} catch (ErroComunicacaoMicroservicesException e) {
			
			
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
			
		}

	}

}
