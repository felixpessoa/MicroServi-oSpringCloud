package com.felixpessoa.mscliente.dto;

import com.felixpessoa.mscliente.model.Cliente;

import lombok.Data;

@Data
public class ClienteSaveReuest {
	
	private String cpf;
	private String nome;
	private Integer idade;
	
	public Cliente toModel() {
		return new Cliente(cpf, nome, idade);
	}
	
}
