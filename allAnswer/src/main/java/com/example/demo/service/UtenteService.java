package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Utente;
import com.example.demo.repository.UtenteRepository;

@Service
public class UtenteService {

	@Autowired
	private UtenteRepository utenteRepository;
	
	@Transactional
	public Utente inserisci(Utente utente) {
		return utenteRepository.save(utente);
	}
	
	@Transactional
	public Utente getById(Long id) {
		return utenteRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public List<Utente> tutti(){
		return (List<Utente>) utenteRepository.findAll();
	}
	
	@Transactional
	public Utente getByEmail(String email) {
		return utenteRepository.findByEmail(email);
	}
}
