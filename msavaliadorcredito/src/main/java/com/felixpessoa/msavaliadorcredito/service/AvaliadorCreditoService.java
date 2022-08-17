package com.felixpessoa.msavaliadorcredito.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.felixpessoa.msavaliadorcredito.model.Cartao;
import com.felixpessoa.msavaliadorcredito.model.CartaoAprovado;
import com.felixpessoa.msavaliadorcredito.model.CartaoCliente;
import com.felixpessoa.msavaliadorcredito.model.DadosCliente;
import com.felixpessoa.msavaliadorcredito.model.RetornoAvaliacaoCliente;
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
			throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
		try {
			ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceFeign.dadosCliente(cpf);
			ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesResourceFeign.getCartoesByCliente(cpf);

			return SituacaoCliente.builder().cliente(dadosClienteResponse.getBody()).cartoes(cartoesResponse.getBody())
					.build();

		} catch (FeignException.FeignClientException e) {
			int status = e.status();
			if (HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
			throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
		}

	}

	public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda)
			throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
		try {
			ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceFeign.dadosCliente(cpf);
			ResponseEntity<List<Cartao>> cartoesResponse = cartoesResourceFeign.getCartaoRendaAteh(renda);

			List<Cartao> cartoes = cartoesResponse.getBody();
			var listaCartoesAprovados = cartoes.stream().map(cartao -> {
				DadosCliente cliente = dadosClienteResponse.getBody();

				BigDecimal limiteBasico = cartao.getLimiteBasico();
				BigDecimal idadeBD = BigDecimal.valueOf(cliente.getIdade());
				var fator = idadeBD.divide(BigDecimal.valueOf(10));
				BigDecimal limiteAprovado = fator.multiply(limiteBasico);

				CartaoAprovado aprovado = new CartaoAprovado();
				aprovado.setCartao(cartao.getNome());
				aprovado.setBandeira(cartao.getBandeiraCartao());
				aprovado.setLimiteAprovado(limiteAprovado);

				return aprovado;
			}).collect(Collectors.toList());

			return new RetornoAvaliacaoCliente(listaCartoesAprovados);

		} catch (FeignException.FeignClientException e) {
			int status = e.status();
			if (HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
			throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
		}

	}

}
