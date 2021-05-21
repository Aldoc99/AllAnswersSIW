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
public class Topic {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String titolo;
	
	private String descrizione;
	
	@OneToMany
	@JoinColumn(name= "topic_id")
	private List<Domanda> domande;
	
	public Topic() {
		
	}

	public Topic(String titolo, String descrizione) {
		this.titolo = titolo;
		this.descrizione = descrizione;
	}
	
	
}
