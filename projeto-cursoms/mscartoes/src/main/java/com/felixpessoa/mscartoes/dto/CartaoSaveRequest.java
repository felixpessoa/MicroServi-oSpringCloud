package com.felixpessoa.mscartoes.dto;

import java.math.BigDecimal;

import com.felixpessoa.mscartoes.model.Cartao;
import com.felixpessoa.mscartoes.model.enuns.BandeiraCartao;

import lombok.Data;

@Data
public class CartaoSaveRequest {
	
	private String nome;
	private BandeiraCartao bandeira;
	private BigDecimal renda;
	private BigDecimal limite;
	
	public Cartao toModel () {
		return new Cartao( nome, bandeira, renda, limite);
	}

}
