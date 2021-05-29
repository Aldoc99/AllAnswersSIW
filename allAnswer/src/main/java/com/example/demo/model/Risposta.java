package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Risposta {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String testo;
	
	private int votiPositivi;
	
	private int votiNegativi;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name= "risposta_id")
	private List<Voto> voti;
	
	@ManyToOne
	private Domanda domanda;
	
	public Risposta() {
		this.votiPositivi=0;
		this.votiNegativi=0;
		this.voti=new ArrayList<>();
	}

	public Risposta(String testo) {
		this.testo = testo;
		this.votiPositivi=0;
		this.votiNegativi=0;
		this.voti=new ArrayList<>();
	}
	
	public void aggiungiVoto(Voto v) {
		if(v.isUtile())
			this.votiPositivi++;
		else
			this.votiNegativi++;
		
		this.voti.add(v);
	}
}
