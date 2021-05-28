package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/list")
	public String list() {
		return "index";
	}
	
	@GetMapping("/login")
	public String prova() {
		return "login";
	}
	
	@GetMapping("/privato")
	public String test() {
		return "privato";
	}
	
	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@GetMapping("/loginSuccess")
	public String getLoginInfo(Model model, OAuth2AuthenticationToken authentication) {
	    OAuth2AuthorizedClient client = authorizedClientService
	      .loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());

	    		    
	    System.out.println(client.getPrincipalName());
	    return "privato";
	}
}
