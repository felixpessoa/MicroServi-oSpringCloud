package com.felixpessoa.msavaliadorcredito.service.exception;

public class DadosClienteNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;

	public DadosClienteNotFoundException() {
		super("Dados do cliente não encontrados para o CPF informado.");
	}

	
	

}
