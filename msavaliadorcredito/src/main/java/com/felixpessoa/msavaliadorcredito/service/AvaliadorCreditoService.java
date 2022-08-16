package com.felixpessoa.msavaliadorcredito.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.felixpessoa.msavaliadorcredito.model.CartaoCliente;
import com.felixpessoa.msavaliadorcredito.model.DadosCliente;
import com.felixpessoa.msavaliadorcredito.model.SituacaoCliente;
import com.felixpessoa.msavaliadorcredito.repositoryfeign.CartoesResourceFeign;
import com.felixpessoa.msavaliadorcredito.repositoryfeign.ClienteResourceFeign;
import com.felixpessoa.msavaliadorcredito.service.exception.DadosClienteNotFoundException;
import com.felixpessoa.msavaliadorcredito.service.exception.ErroComunicacaoMicroservicesException;

import feign.FeignException;

@Service
public class AvaliadorCreditoService {
	
	@Autowired
	private ClienteResourceFeign clienteResourceFeign;
	@Autowired
	private CartoesResourceFeign cartoesResourceFeign;

	public SituacaoCliente obterSituacaoCliente(String cpf) 
			throws  DadosClienteNotFoundException, ErroComunicacaoMicroservicesException{
		try {
			ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceFeign.dadosCliente(cpf);
			ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesResourceFeign.getCartoesByCliente(cpf);
			
			return SituacaoCliente
					.builder()
					.cliente(dadosClienteResponse.getBody())
					.cartoes(cartoesResponse.getBody())
					.build();
			
		} catch (FeignException.FeignClientException e) {
			int status = e.status();
			if (HttpStatus.NOT_FOUND.value() == status ) {
				throw new DadosClienteNotFoundException();
			}
			throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
		}
		
		
	}

}
