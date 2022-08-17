package com.felixpessoa.mscartoes.util.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felixpessoa.mscartoes.model.Cartao;
import com.felixpessoa.mscartoes.model.ClienteCartao;
import com.felixpessoa.mscartoes.model.DadosSolicitacaoEmissaoCartao;
import com.felixpessoa.mscartoes.repository.CartaoRepository;
import com.felixpessoa.mscartoes.repository.ClienteCartaoRepository;

@Component
public class EmissaoCartaoSubscriber {

	@Autowired
	private CartaoRepository cartaoRepository;
	@Autowired
	private ClienteCartaoRepository clienteCartaoRepository;

	@RabbitListener(queues = "${mq.queues.emissao-cartoes}")
	public void receberSolicitacaoEmissao(@Payload String payload) {
		try {

			var mapper = new ObjectMapper();
			DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
			Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();
			ClienteCartao clienteCartao = new ClienteCartao();
			clienteCartao.setCartao(cartao);
			clienteCartao.setCpf(dados.getCpf());
			clienteCartao.setLimite(dados.getLimiteLiberado());
			clienteCartaoRepository.save(clienteCartao);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
