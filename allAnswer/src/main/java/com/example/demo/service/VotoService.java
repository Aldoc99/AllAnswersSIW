package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Voto;
import com.example.demo.repository.VotoRepository;

@Service
public class VotoService {

	@Autowired
	private VotoRepository votoRepository;
	
	@Transactional
	public Voto inserisci(Voto voto) {
		return votoRepository.save(voto);
	}
	
	@Transactional
	public Voto getById(Long id) {
		return votoRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public List<Voto> tutti(){
		return (List<Voto>) votoRepository.findAll();
	}
	
	@Transactional
	public void rimuovi(Voto v) {
		votoRepository.delete(v);
	}
}
