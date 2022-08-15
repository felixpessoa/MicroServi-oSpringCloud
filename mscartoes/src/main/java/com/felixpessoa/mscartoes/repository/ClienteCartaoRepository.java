package com.felixpessoa.mscartoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.felixpessoa.mscartoes.model.ClienteCartao;

public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, Long> {

	List<ClienteCartao> findByCpf (String cpf);
	

}
