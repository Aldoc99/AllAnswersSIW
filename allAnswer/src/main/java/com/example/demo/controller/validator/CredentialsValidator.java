package com.example.demo.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Credentials;
import com.example.demo.service.CredentialsService;

@Component
public class CredentialsValidator implements Validator{

	@Autowired
	private CredentialsService credentialsService;

	@Override
	public boolean supports(Class<?> clazz) {
		 return Credentials.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Credentials credentials = (Credentials) target;
		String email = credentials.getEmail().trim();
        String password = credentials.getPassword().trim();
        String username=credentials.getUsername().trim();
		
        if(username.isEmpty())
        	errors.rejectValue("username","required");
        else if (this.credentialsService.getByUsername(username)!=null)
        	errors.rejectValue("username", "duplicate");
        	
        if (email.isEmpty())
            errors.rejectValue("email", "required");
        else if (this.credentialsService.getByEmail(email) != null)
            errors.rejectValue("email", "duplicate");
        
        if (password.isEmpty())
            errors.rejectValue("password", "required");
	}

}
