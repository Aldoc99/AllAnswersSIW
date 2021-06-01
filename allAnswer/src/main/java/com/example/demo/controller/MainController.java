package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.authentication.CustomOAuth2User;
import com.example.demo.model.Topic;
import com.example.demo.model.Utente;
import com.example.demo.service.DomandaService;
import com.example.demo.service.TopicService;
import com.example.demo.service.UtenteService;
import com.example.demo.session.SessionData;

@Controller
public class MainController {

	@Autowired
	private DomandaService domandaService;
	
	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	SessionData sessionData;

	@Autowired
	private TopicService topicService;
	
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
	    CustomOAuth2User user=(CustomOAuth2User)authentication.getPrincipal();
	    Utente utente=utenteService.getByEmail(user.getEmail());
	    	if(utente==null) {
	    		utente=new Utente(user.getName(),user.getEmail());
	    		utenteService.inserisci(utente);
	    	}
	    		
	    		sessionData.setUtente(utente);
	    		model.addAttribute("domande",domandaService.getRandomDomande());
	    return "index";
	}
	
	@GetMapping("/explore")
	public String explore(Model model) {
		model.addAttribute("topics", topicService.tutti());
		return "explore";
	}
	
	@GetMapping("/topic/{idT}")
	public String viewQuestion(@PathVariable("idT") Long id,Model model) {
		Topic topic=this.topicService.getById(id);
		model.addAttribute("topic", topic);
    	model.addAttribute("domande",topic.getDomande());
        return "topic";
    }
	
	
	
}
