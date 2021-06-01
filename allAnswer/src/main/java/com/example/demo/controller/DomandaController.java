package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.Domanda;
import com.example.demo.model.Risposta;
import com.example.demo.model.Utente;
import com.example.demo.service.DomandaService;
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
    
 /*   @RequestMapping(value = "/risposta", method = RequestMethod.GET)
    public String getRisposte(Model model) {
    		model.addAttribute("risposte", this.domandaService.tutti());
    		return "risposte.html";
    }*/
    
    
	
	

}
