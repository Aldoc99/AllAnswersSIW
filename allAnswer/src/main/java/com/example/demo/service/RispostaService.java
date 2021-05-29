package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Risposta;
import com.example.demo.repository.RispostaRepository;

@Service
public class RispostaService {

	@Autowired
	private RispostaRepository rispostaRepository;
	
	@Transactional
	public Risposta inserisci(Risposta risposta) {
		return rispostaRepository.save(risposta);
	}
	
	@Transactional
	public Risposta getById(Long id) {
		return rispostaRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public List<Risposta> tutti(){
		return (List<Risposta>) rispostaRepository.findAll();
	}
	
}
