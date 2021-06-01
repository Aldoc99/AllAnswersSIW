package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Domanda;
import com.example.demo.model.Risposta;
import com.example.demo.model.Utente;
import com.example.demo.service.DomandaService;
import com.example.demo.service.UtenteService;
import com.example.demo.session.SessionData;

@Controller
public class RispostaController {
	
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	SessionData sessionData;


	@Autowired
	private DomandaService domandaService;


	@Autowired
	private RispostaValidator rispostaValidator;
	
	@GetMapping("/risposte")
	public String getRisposte(Model model) {
		Utente utente=sessionData.getUtente();
		utente=utenteService.getByEmail(utente.getEmail());
		List<Risposta> risposte=utente.getRisposte();

		
		
		model.addAttribute("risposte", risposte);
		
		return "risposte";
	}
	
	@GetMapping("/{id}/addRisposta")
	public String addRisposta(@PathVariable("id") Long id,Model model) {
		model.addAttribute("domanda", domandaService.getById(id));
		model.addAttribute("risposta", new Risposta());
		return "rispostaForm";
	}
	
	@PostMapping("/{id}/addRisposta")
	public String aggiungiRisposta(@PathVariable("id") Long id,Model model,@ModelAttribute("risposta") Risposta risposta,
			BindingResult bindingResult) {
		Domanda domanda=domandaService.getById(id);
		this.rispostaValidator.validate(risposta, bindingResult);
        if (!bindingResult.hasErrors()) {
        	
        	domanda.getRisposte().add(risposta);
        	risposta.setDomanda(domanda);
        	
        	domandaService.inserisci(domanda);
        	model.addAttribute("domanda", domanda);
        	return "domanda";
        }
        model.addAttribute("domanda",domanda);
        
        return "rispostaForm";
	}
}
