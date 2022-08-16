package com.felixpessoa.msavaliadorcredito.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.felixpessoa.msavaliadorcredito.model.CartaoCliente;
import com.felixpessoa.msavaliadorcredito.model.DadosCliente;
import com.felixpessoa.msavaliadorcredito.model.SituacaoCliente;
import com.felixpessoa.msavaliadorcredito.repositoryfeign.CartoesResourceFeign;
import com.felixpessoa.msavaliadorcredito.repositoryfeign.ClienteResourceFeign;

@Service
public class AvaliadorCreditoService {
	
	@Autowired
	private ClienteResourceFeign clienteResourceFeign;
	@Autowired
	private CartoesResourceFeign cartoesResourceFeign;

	public SituacaoCliente obterSituacaoCliente(String cpf) {
		ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceFeign.dadosCliente(cpf);
		ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesResourceFeign.getCartoesByCliente(cpf);
		return SituacaoCliente
				.builder()
				.cliente(dadosClienteResponse.getBody())
				.cartoes(cartoesResponse.getBody())
				.build();
	}

}
