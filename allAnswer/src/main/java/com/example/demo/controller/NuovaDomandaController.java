package com.example.demo.controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Domanda;
import com.example.demo.model.Topic;
import com.example.demo.service.DomandaService;
import com.example.demo.service.TopicService;
import com.example.demo.service.UtenteService;
import com.example.demo.session.SessionData;

@Controller
public class NuovaDomandaController {

	@Autowired
	TopicService topicService;
	
	@Autowired
	private DomandaService domandaService;
	
	@Autowired
	private UtenteService utenteService;
	
    @Autowired
    private DomandaValidator domandaValidator;
    
    @Autowired
    SessionData sessionData;
    
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/addQuestion")
	public String chooseTopic(Model model) {
		model.addAttribute("topics", topicService.tutti());
		return "domandaFormTopic";
	}
	

	@GetMapping("/addQuestion/{id}")
	public String addQuestion(@PathVariable("id") Long id,Model model) {
//    	logger.debug("addPersona");
		Topic topicCorrente=this.topicService.getById(id);
		sessionData.setTopic(topicCorrente);
		model.addAttribute("topic", topicCorrente);
    	model.addAttribute("domanda", new Domanda());
        return "domandaForm";
    }
	
	@PostMapping("/domanda")
	public String newQuestion(@ModelAttribute("domanda") Domanda domanda, 
			Model model, BindingResult bindingResult) {
		
		this.domandaValidator.validate(domanda, bindingResult);
        if (!bindingResult.hasErrors()) {
        	domanda.setData(LocalDate.now());
        	
        	sessionData.getUtente().getDomande().add(domanda);
        	this.utenteService.inserisci(sessionData.getUtente());
        	
        	sessionData.getTopic().getDomande().add(domanda);
        	this.topicService.inserisci(sessionData.getTopic());
        	
        	model.addAttribute("domande", this.domandaService.tutti());
            
        	return "domanda";
        }
		
		return "domandaForm";
	}
	

	
	
	
}
