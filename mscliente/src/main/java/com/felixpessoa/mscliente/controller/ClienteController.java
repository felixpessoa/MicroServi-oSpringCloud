package com.felixpessoa.mscliente.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.felixpessoa.mscliente.dto.ClienteSaveReuest;
import com.felixpessoa.mscliente.model.Cliente;
import com.felixpessoa.mscliente.service.ClienteService;

@RestController
@RequestMapping("clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping
	public String status() {
		return "ok";
	}
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody ClienteSaveReuest request) {
		Cliente cliente = clienteService.save(request.toModel());
		URI headerLocation = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.query("cpf={cpf}")
				.buildAndExpand(cliente.getCpf())
				.toUri();
		return ResponseEntity.created(headerLocation).build();
	}
	
	@GetMapping(params = "cpf")
	public ResponseEntity<?> dadosCliente(@RequestParam("cpf") String cpf){
		var cliente = clienteService.getByCpf(cpf);
		if(cliente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}	
		return ResponseEntity.ok(cliente);
	}

}
