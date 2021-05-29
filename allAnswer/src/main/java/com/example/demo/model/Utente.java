package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Utente {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	private String cognome;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	private String username;
	
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

	public Utente(String nome,String email) {
		this.nome=nome;
		this.email=email;
	}
	public Utente(String nome, String cognome, String email, String password, String username) {
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.password = password;
		this.username = username;
		this.voti=new ArrayList<>();
		this.risposte=new ArrayList<>();
		this.domande=new ArrayList<>();
	}
	
}
