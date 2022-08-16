package com.felixpessoa.msavaliadorcredito.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.felixpessoa.msavaliadorcredito.feign.ClienteResourceFeign;
import com.felixpessoa.msavaliadorcredito.model.DadosCliente;
import com.felixpessoa.msavaliadorcredito.model.SituacaoCliente;

@Service
public class AvaliadorCreditoService {
	
	@Autowired
	private ClienteResourceFeign clienteResourceFeign;

	public SituacaoCliente obterSituacaoCliente(String cpf) {
		ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceFeign.dadosCliente(cpf);
		
		return SituacaoCliente
				.builder()
				.cliente(dadosClienteResponse.getBody())
				.build();
	}

}
