package com.felixpessoa.msavaliadorcredito.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Cartao {
	
	private Long id;
	private String nome;
	private String bandeiraCartao;
	private BigDecimal limiteBasico;

}
