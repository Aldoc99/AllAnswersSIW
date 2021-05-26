package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Domanda;
import com.example.demo.repository.DomandaRepository;

@Service
public class DomandaService {

	@Autowired
	private DomandaRepository domandaRepository;
	
	@Transactional
	public Domanda inserisci(Domanda domanda) {
		return domandaRepository.save(domanda);
	}
	
	@Transactional
	public Domanda getById(Long id) {
		return domandaRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public List<Domanda> tutti(){
		return (List<Domanda>) domandaRepository.findAll();
	}
	
}
