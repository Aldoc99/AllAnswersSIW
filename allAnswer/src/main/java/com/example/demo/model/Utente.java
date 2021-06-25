package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Utente {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@OneToOne(mappedBy="utente")
	private Credentials credentials;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name= "utente_id")
	private List<Voto> voti;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name= "utente_id")
	private List<Risposta> risposte;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name= "utente_id")
	private List<Domanda> domande;
	
	public Utente() {
		this.voti=new ArrayList<>();
		this.risposte=new ArrayList<>();
		this.domande=new ArrayList<>();	
	}
	
	public String getEmail() {
		return this.credentials.getEmail();
	}
	
	
}
