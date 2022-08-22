package com.felixpessoa.mscartoes.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.felixpessoa.mscartoes.dto.CartaoSaveRequest;
import com.felixpessoa.mscartoes.dto.CartoesPorClienteResponse;
import com.felixpessoa.mscartoes.model.Cartao;
import com.felixpessoa.mscartoes.model.ClienteCartao;
import com.felixpessoa.mscartoes.service.CartaoService;
import com.felixpessoa.mscartoes.service.ClienteCartaoService;

@RestController
@RequestMapping("cartoes")
public class CartoesController {
	
	@Autowired
	private CartaoService cartaoService;
	@Autowired
	private ClienteCartaoService clienteCartaoService;
	
	
	@GetMapping
	public String status() {
		return "ok";
	}
	
	@PostMapping
	public ResponseEntity<?> cadastra(@RequestBody CartaoSaveRequest request ){
		Cartao cartao = request.toModel();
		cartaoService.save(cartao);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(params = "renda")
	public ResponseEntity<List<Cartao>> getCartaoRendaAteh(@RequestParam("renda") Long renda ){
		List<Cartao> list = cartaoService.getCartoesRendaMenorIgual(renda);
		return ResponseEntity.ok(list);
	}
	
	
	@GetMapping(params = "cpf")
	public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente(@RequestParam("cpf") String cpf){
		List<ClienteCartao> list = clienteCartaoService.listCartaoCpf(cpf);
		List<CartoesPorClienteResponse> result = list.stream()
				.map(CartoesPorClienteResponse::fromModel)
				.collect(Collectors.toList());
		return ResponseEntity.ok(result);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
