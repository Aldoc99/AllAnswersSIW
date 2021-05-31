package com.example.demo.session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.example.demo.model.Utente;

@Component
@Scope(value="session",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {

	private Utente utente;

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
}