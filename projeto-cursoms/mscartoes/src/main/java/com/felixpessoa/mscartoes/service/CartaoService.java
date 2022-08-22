package com.felixpessoa.mscartoes.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felixpessoa.mscartoes.model.Cartao;
import com.felixpessoa.mscartoes.repository.CartaoRepository;

@Service
public class CartaoService {
	
	@Autowired
	private CartaoRepository repository; 

	@Transactional
	public Cartao save(Cartao cartao) {
		return repository.save(cartao);
	}
	
	public List<Cartao> getCartoesRendaMenorIgual(long renda){
		var rendaBigDecimal = BigDecimal.valueOf(renda);
		return repository.findByRendaLessThanEqual(rendaBigDecimal);
	}
	
}
