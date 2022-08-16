package com.felixpessoa.msavaliadorcredito.repositoryfeign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.felixpessoa.msavaliadorcredito.model.CartaoCliente;

@FeignClient(value = "mscartoes", path = "/cartoes")
public interface CartoesResourceFeign {
	
	@GetMapping(params = "cpf")
	ResponseEntity<List<CartaoCliente>> getCartoesByCliente(@RequestParam("cpf") String cpf);

}
