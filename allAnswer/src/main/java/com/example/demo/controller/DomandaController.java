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

import com.example.demo.model.Domanda;
import com.example.demo.model.Risposta;
import com.example.demo.model.Topic;
import com.example.demo.model.Utente;
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
	
    @RequestMapping(value = "/domanda", method = RequestMethod.GET)
    public String getDomande(Model model) {
    	Utente utente=sessionData.getUtente();
    	utente=utenteService.getByEmail(utente.getEmail());
		List<Domanda> domande=utente.getDomande();
    	model.addAttribute("domande", domande);
    	return "domande.html";
    }
    
    @RequestMapping(value = "/domanda/{id}", method = RequestMethod.GET)
    public String apriDomanda(@PathVariable("id") Long id,Model model) {
            model.addAttribute("domanda",this.domandaService.getById(id));
            
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
		sessionData.setTopic(topicCorrente);
		model.addAttribute("topic", topicCorrente);
    	model.addAttribute("domanda", new Domanda());
        return "domandaForm";
    }
	
	@PostMapping("/{idT}/addQuestion")
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 /*   @RequestMapping(value = "/risposta", method = RequestMethod.GET)
    public String getRisposte(Model model) {
    		model.addAttribute("risposte", this.domandaService.tutti());
    		return "risposte.html";
    }*/
    
    
	
	

}
