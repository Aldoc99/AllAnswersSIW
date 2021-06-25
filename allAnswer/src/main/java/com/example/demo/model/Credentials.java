package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Credentials {

	public static final String DEFAULT_ROLE = "DEFAULT";
	public static final String ADMIN_ROLE = "ADMIN";
	private static final String DEFAULT_PWD = "pwd";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String email;

	@Column(unique=true)
	private String username;
	
	private String password;

	private String ruolo;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Utente utente;
	
	public Credentials() {
		this.ruolo=DEFAULT_ROLE;
	}

	public Credentials(String email) {
		this.email = email;
		this.password=DEFAULT_PWD;
		this.ruolo=DEFAULT_ROLE;
	}
	
	public Credentials(String email, String password,String ruolo) {
		this.email = email;
		this.password = password;
		this.ruolo=ruolo;
	}

}
