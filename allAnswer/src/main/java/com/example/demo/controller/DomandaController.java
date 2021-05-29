package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.service.DomandaService;

@Controller
public class DomandaController {
	
	@Autowired
	private DomandaService domandaService;
	
    @RequestMapping(value = "/domanda", method = RequestMethod.GET)
    public String getDomande(Model model) {
    	
    		model.addAttribute("domande", this.domandaService.tutti());
    		return "domande.html";
    }
    
    @RequestMapping(value = "/domanda/{id}", method = RequestMethod.GET)
    public String apriDomanda(@PathVariable("id") Long id,Model model) {

    	//model.addAttribute("risposte", domanda.getRisposte());
    		return "domanda.html";
    }
    
 /*   @RequestMapping(value = "/risposta", method = RequestMethod.GET)
    public String getRisposte(Model model) {
    		model.addAttribute("risposte", this.domandaService.tutti());
    		return "risposte.html";
    }*/
    
    
	
	

}
