package com.example.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Domanda {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String titolo;
	
	private String testo;
	
	@OneToMany
	@JoinColumn(name= "domanda_id")
	private List<Risposta> risposte;
	

	
	public Domanda() {
		
	}

	public Domanda(String titolo, String testo) {
		this.titolo = titolo;
		this.testo = testo;
	}
	
	
}
