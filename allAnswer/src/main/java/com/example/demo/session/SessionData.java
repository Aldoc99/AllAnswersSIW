package com.example.demo.session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.example.demo.model.Credentials;
import com.example.demo.model.Utente;

@Component
@Scope(value="session",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {

	private Credentials credentials;

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	
}
