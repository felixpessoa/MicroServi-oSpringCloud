package com.felixpessoa.mscartoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felixpessoa.mscartoes.model.ClienteCartao;
import com.felixpessoa.mscartoes.repository.ClienteCartaoRepository;

@Service
public class ClienteCartaoService {

	@Autowired
	private ClienteCartaoRepository repository;
	
	
	public List<ClienteCartao> listCartaoCpf(String cpf){
		return repository.findByCpf(cpf);
	}
	
}
