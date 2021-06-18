package com.example.demo.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Credentials;
import com.example.demo.service.CredentialsService;

@Component
public class OAuthValidator implements Validator {

	@Autowired
	private CredentialsService credentialsService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Credentials.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Credentials credentials = (Credentials) target;
		String username = credentials.getUsername();

		if (username.isEmpty())
			errors.rejectValue("username", "required");
		else {
			if ( this.credentialsService.getByUsername(username)!= null)
				errors.rejectValue("username", "duplicate");
		}
			

	}

}
