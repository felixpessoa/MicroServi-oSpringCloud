package com.felixpessoa.mscartoes.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.felixpessoa.mscartoes.model.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

	List<Cartao> findByRendaLessThanEqual(BigDecimal renda);
	

}
