package com.felixpessoa.mscliente.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Cliente implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cpf;
	private String nome;
	private int idade;

	public Cliente(String cpf, String nome, int idade) {
		super();
		this.cpf = cpf;
		this.nome = nome;
		this.idade = idade;
	}
}
