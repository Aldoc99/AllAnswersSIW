package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.Credentials;
import com.example.demo.model.Domanda;
import com.example.demo.model.Topic;
import com.example.demo.model.Utente;
import com.example.demo.service.CredentialsService;
import com.example.demo.service.DomandaService;
import com.example.demo.service.TopicService;
import com.example.demo.service.UtenteService;
import com.example.demo.session.SessionData;

@Controller
public class DomandaController {
	
	@Autowired
	private DomandaService domandaService;
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	SessionData sessionData;
	
	@Autowired
	TopicService topicService;
	
    @Autowired
    private DomandaValidator domandaValidator;

    @Autowired
	private CredentialsService credentialsService;
	
    @RequestMapping(value = "/domanda", method = RequestMethod.GET)
    public String getDomande(Model model) {
    	Credentials credentials=sessionData.getCredentials();
		credentials=credentialsService.getByEmail(credentials.getEmail());
		Utente utente=credentials.getUtente();
		List<Domanda> domande=utente.getDomande();
    	model.addAttribute("domande", domande);
    	return "domande.html";
    }
    
    @RequestMapping(value = "/domanda/{id}", method = RequestMethod.GET)
    public String apriDomanda(@PathVariable("id") Long id,Model model) {
            model.addAttribute("domanda",this.domandaService.getById(id));
            Credentials credentials=sessionData.getCredentials();
    		credentials=credentialsService.getByEmail(credentials.getEmail());
    		Utente utente=credentials.getUtente();
            model.addAttribute("utente", utente);
    		return "domanda.html";
    }
    
    
    
    @GetMapping("/addQuestion")
	public String chooseTopic(Model model) {
		model.addAttribute("topics", topicService.tutti());
		return "domandaFormTopic";
	}
	

	@GetMapping("/{idT}/addQuestion")
	public String addQuestion(@PathVariable("idT") Long id,Model model) {
//    	logger.debug("addPersona");
		Topic topicCorrente=this.topicService.getById(id);
		model.addAttribute("topic", topicCorrente);
    	model.addAttribute("domanda", new Domanda());
        return "domandaForm";
    }
	
	@PostMapping("/{idT}/addQuestion")
	public String newQuestion(@PathVariable("idT") Long id,@ModelAttribute("domanda") Domanda domanda, 
			Model model, BindingResult bindingResult) {
		Topic topic=topicService.getById(id);
		this.domandaValidator.validate(domanda, bindingResult);
        if (!bindingResult.hasErrors()) {
        	domanda.setData(LocalDate.now());
        	
        	domanda=domandaService.inserisci(domanda);
        	Credentials credentials=sessionData.getCredentials();
    		credentials=credentialsService.getByEmail(credentials.getEmail());
    		
    		Utente utente=credentials.getUtente();
        	utente.getDomande().add(domanda);
        	this.utenteService.inserisci(utente);
        	
        	domanda=domandaService.getById(domanda.getId());
        	topic.getDomande().add(domanda);
        	topicService.inserisci(topic);
        	
        	model.addAttribute("utente", utente);
        	return "domanda";
        }
        
        
        
        model.addAttribute("topic", topic);
        
		return "domandaForm";
	}
    
    @GetMapping("/cancellaDomanda/{id}")
    public String cancellaDomanda(@PathVariable("id") Long id,Model model) {
    	Credentials credentials=sessionData.getCredentials();
		credentials=credentialsService.getByEmail(credentials.getEmail());
		Utente utente=credentials.getUtente();
		
    	Domanda domanda=domandaService.getById(id);
    	List<Domanda> domande=utente.getDomande();
    	if(domande.contains(domanda))
    		domandaService.cancellaDomanda(domanda);
    	
    	model.addAttribute("domande", domande);
    	
    	return "domande";
    }
    
	
	

}
