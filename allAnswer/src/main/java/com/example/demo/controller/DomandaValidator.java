package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.model.Domanda;

@Component
public class DomandaValidator implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(DomandaValidator.class);
	
	
	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titolo", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "testo", "required");

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Domanda.class.equals(aClass);
	}
}
