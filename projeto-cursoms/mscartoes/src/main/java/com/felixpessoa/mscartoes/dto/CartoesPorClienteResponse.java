package com.felixpessoa.mscartoes.dto;

import java.math.BigDecimal;

import com.felixpessoa.mscartoes.model.ClienteCartao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartoesPorClienteResponse {
	
	private String nome;
	private String bandeira;
	private BigDecimal limiteLiberado;
	
	public static CartoesPorClienteResponse fromModel(ClienteCartao model) {
		return new CartoesPorClienteResponse(
				model.getCartao().getNome(), 
				model.getCartao().getBandeiraCartao().toString(), 
				model.getLimite());
	}

}
