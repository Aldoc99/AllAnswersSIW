package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.model.Topic;
import com.example.demo.service.TopicService;

@Component
public class TopicValidator implements Validator{

	@Autowired
	private TopicService topicService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Validator.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Topic topic=(Topic) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descrizione", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titolo", "required");
		
		if(!errors.hasErrors())
			if(topicService.alreadyExists(topic))
				errors.reject("duplicato");
		
		
	}

}
