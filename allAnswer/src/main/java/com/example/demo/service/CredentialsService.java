package com.example.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Credentials;
import com.example.demo.repository.CredentialsRepository;



@Service
public class CredentialsService {

	@Autowired
    protected PasswordEncoder passwordEncoder;

	@Autowired
	protected CredentialsRepository credentialsRepository;
	
	@Transactional
	public Credentials getCredentials(Long id) {
		return this.credentialsRepository.findById(id).orElse(null);
	}

		
    @Transactional
    public Credentials inserisci(Credentials credentials) {
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }
    
    @Transactional
    public Credentials getByEmail(String email) {
    	return this.credentialsRepository.findByEmail(email).orElse(null);
    }
    
    @Transactional
    public Credentials getByUsername(String username) {
    	return this.credentialsRepository.findByUsername(username).orElse(null);
    }
}
