package com.example.demo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Domanda {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String titolo;
	
	@Column(length=1000)
	private String testo;
	
	private LocalDate data;
	
	@OneToMany(mappedBy="domanda",cascade = {CascadeType.MERGE,CascadeType.REMOVE})
	private List<Risposta> risposte;
	
	@ManyToOne
	private Utente utente;
	
	public Domanda() {
		this.risposte=new ArrayList<>();
	}

	public Domanda(String titolo, String testo) {
		this.titolo = titolo;
		this.testo = testo;
		this.risposte=new ArrayList<>();
	}
	
	
}
