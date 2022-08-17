package com.felixpessoa.msavaliadorcredito.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DadosSolicitacaoEmissaoCartao {
	
	private Long id;
	private String cpf;
	private String endereco;
	private BigDecimal limiteLiberado;

}
