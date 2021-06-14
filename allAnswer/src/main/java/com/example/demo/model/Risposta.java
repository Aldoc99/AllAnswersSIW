package com.example.demo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Risposta {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(length=1000)
	private String testo;
	
	private int votiPositivi;
	
	private int votiNegativi;
	
	private LocalDate data;
	
	@OneToMany(mappedBy="risposta")
	private List<Voto> voti;
	
	@ManyToOne
	private Domanda domanda;
	
	@ManyToMany
	private List<Utente> utentiVotanti;
	
	public Risposta() {
		this.votiPositivi=0;
		this.votiNegativi=0;
		this.voti=new ArrayList<>();
		this.utentiVotanti=new ArrayList<>();
		this.data=LocalDate.now();
	}

	public Risposta(String testo) {
		this.testo = testo;
		this.votiPositivi=0;
		this.votiNegativi=0;
		this.data=LocalDate.now();
		this.voti=new ArrayList<>();
		this.utentiVotanti=new ArrayList<>();
	}
	
	public void aggiungiVoto(Voto v) {
		if(v.isUtile())
			this.votiPositivi++;
		else
			this.votiNegativi++;
		
		this.voti.add(v);
	}
	public void togliVoto(Voto v) {
		if(v.isUtile())
			this.votiPositivi--;
		else
			this.votiNegativi--;
		
		this.voti.remove(v);
	}
	public void cambiaVoto(Voto v) {
		if(v.isUtile()) {
			this.votiPositivi--;
			this.votiNegativi++;
		}
			
		else {
			this.votiNegativi--;
			this.votiPositivi++;
		}
	}
}
