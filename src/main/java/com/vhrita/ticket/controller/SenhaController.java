package com.vhrita.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vhrita.ticket.model.Senha;
import com.vhrita.ticket.repository.SenhaRepository;

@RestController
@RequestMapping("/senhas")
public class SenhaController {
	
	@Autowired
	private SenhaRepository senhaRepository;
	
	
	@GetMapping
	public List<Senha> listar() {
		return senhaRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Senha criar(@RequestBody Senha senha) {
		int lastTicketNumber;
		Senha lastTicket;
		if(senha.getTipo()=='N') {
			lastTicket = senhaRepository.findLastTicketN();
		} else {
			lastTicket = senhaRepository.findLastTicketP();
		}
		
		if(lastTicket!=null) {
			lastTicketNumber = lastTicket.getNumero();
		} else {
			lastTicketNumber = 0;
		}
		
		senha.setNumero(lastTicketNumber+1);
		return senhaRepository.save(senha);
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.OK)
	public Senha excluir(@RequestBody(required=false) Senha senha) {
		if(senha!=null) {
			senhaRepository.deleteById(senha.getId());
		} else {
			senhaRepository.deleteAll();
		}
		
		return senha;
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Senha atualizar(@RequestBody Senha senha) {
		senhaRepository.setCurrent(senha.getId());
		return senha;
	}
}
