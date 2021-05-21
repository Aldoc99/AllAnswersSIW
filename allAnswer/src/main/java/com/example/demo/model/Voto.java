package com.example.demo.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Voto {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private boolean utile;
	
	public Voto(){
		
	}

	public Voto(boolean utile) {
		this.utile = utile;
	}
	
	
	
	
}
