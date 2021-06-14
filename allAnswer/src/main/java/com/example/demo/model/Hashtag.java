package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

@Data
@Entity
public class Hashtag {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	@ManyToMany
	List<Domanda> domande;
	
	public Hashtag() {
		this.domande=new ArrayList<>();
	}
	
	public Hashtag(String nome) {
		this.nome=nome;

		this.domande=new ArrayList<>();
	}
	
}
