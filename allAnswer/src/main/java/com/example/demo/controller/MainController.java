package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.DomandaService;

@Controller
public class MainController {

	@Autowired
	private DomandaService domandaService;
	
	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;
	
	
	@GetMapping("/home")
	public String index(Model model) {
		model.addAttribute("domande",domandaService.getRandomDomande());
		return "index";
	}
	
	@GetMapping("/")
	public String benvenuto(Model model) {
		return "login";
	}

	@GetMapping("/loginSuccess")
	public String getLoginInfo(Model model, OAuth2AuthenticationToken authentication) {
	    OAuth2AuthorizedClient client = authorizedClientService
	      .loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());

	    		    
	    System.out.println(client.getPrincipalName());
	    return "privato";
	}
	
	
}
