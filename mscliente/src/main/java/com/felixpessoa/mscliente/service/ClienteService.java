package com.felixpessoa.mscliente.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felixpessoa.mscliente.model.Cliente;
import com.felixpessoa.mscliente.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Transactional
	public Cliente save(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	public Optional<Cliente> getByCpf (String cpf){
		return clienteRepository.findByCpf(cpf);
	}
	
	
}
