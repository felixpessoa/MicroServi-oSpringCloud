package com.felixpessoa.msavaliadorcredito.service.exception;

import lombok.Getter;

public class ErroComunicacaoMicroservicesException extends Exception{
	private static final long serialVersionUID = 1L;
	
	@Getter
	private Integer status;

	public ErroComunicacaoMicroservicesException(String msg, Integer status) {
		super(msg);
		this.status = status;
	}

	
}
