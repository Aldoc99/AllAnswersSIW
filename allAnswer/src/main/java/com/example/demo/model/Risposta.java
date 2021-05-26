package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

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
	
	@OneToMany
	@JoinColumn(name= "risposta_id")
	private List<Voto> voti;
	
	@ManyToOne
	private Domanda domanda;
	
	public Risposta() {
		this.voti=new ArrayList<>();
	}

	public Risposta(String testo) {
		this.testo = testo;
		this.voti=new ArrayList<>();
	}
	
	
}
