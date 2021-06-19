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

import com.example.demo.controller.validator.RispostaValidator;
import com.example.demo.model.Credentials;
import com.example.demo.model.Domanda;
import com.example.demo.model.Risposta;
import com.example.demo.model.Utente;
import com.example.demo.model.Voto;
import com.example.demo.service.CredentialsService;
import com.example.demo.service.DomandaService;
import com.example.demo.service.RispostaService;
import com.example.demo.service.UtenteService;
import com.example.demo.service.VotoService;
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
	private RispostaService rispostaService;


	@Autowired
	private RispostaValidator rispostaValidator;

	@Autowired
	private VotoService votoService;

	@Autowired
	private CredentialsService credentialsService;
	
	@GetMapping("/risposte")
	public String getRisposte(Model model) {
		Credentials credentials=sessionData.getCredentials();
		credentials=credentialsService.getByEmail(credentials.getEmail());
		Utente utente=credentials.getUtente();
		
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
        	Credentials credentials=sessionData.getCredentials();
    		credentials=credentialsService.getByEmail(credentials.getEmail());
    		Utente utente=credentials.getUtente();
        	domanda.getRisposte().add(risposta);
        	risposta.setDomanda(domanda);
        	utente.getRisposte().add(risposta);
        	risposta.setUtente(utente);
        	utenteService.inserisci(utente);
        	model.addAttribute("utente",utente);
        	model.addAttribute("domanda", domanda);
        	return "domanda";
        }
       
        model.addAttribute("domanda",domanda);
        
        return "rispostaForm";
	}
	
	@PostMapping("/{idRisposta}/addVotoPositivo")
	public String addVotoPositivo(Model model,@PathVariable("idRisposta") Long id) {
		Risposta risposta=rispostaService.getById(id);
		Voto v=new Voto(true);
		Credentials credentials=sessionData.getCredentials();
		credentials=credentialsService.getByEmail(credentials.getEmail());
		Utente utente=credentials.getUtente();
		v.setRisposta(risposta);
		v=votoService.inserisci(v);
		risposta.aggiungiVoto(v);
		risposta.getUtentiVotanti().add(utente);
		utente.getVoti().add(v);
		utenteService.inserisci(utente);

		model.addAttribute("domanda", risposta.getDomanda());
		model.addAttribute("utente", utente);
		 
		return "domanda";
	}
	
	
	@PostMapping("/{idRisposta}/addVotoNegativo")
	public String addVotoNegativo(Model model,@PathVariable("idRisposta") Long id) {
		Risposta risposta=rispostaService.getById(id);
		Voto v=new Voto(false);
		Credentials credentials=sessionData.getCredentials();
		credentials=credentialsService.getByEmail(credentials.getEmail());
		Utente utente=credentials.getUtente();
		v.setRisposta(risposta);
		v=votoService.inserisci(v);
		risposta.aggiungiVoto(v);
		risposta.getUtentiVotanti().add(utente);
		utente.getVoti().add(v);
		rispostaService.inserisci(risposta);
		utenteService.inserisci(utente);
		
		model.addAttribute("domanda", risposta.getDomanda());
		model.addAttribute("utente", utente);
		
		return "domanda";
	}
	
	
	
	@PostMapping("/{idRisposta}/togliVoto")
	public String togliVoto(Model model,@PathVariable("idRisposta") Long id) {
		Risposta risposta=rispostaService.getById(id);
		
		Credentials credentials=sessionData.getCredentials();
		credentials=credentialsService.getByEmail(credentials.getEmail());
		Utente utente=credentials.getUtente();
		List<Voto> voti=utente.getVoti();
		for(int i=0;i<voti.size();i++) {
			Voto v=voti.get(i);
			if(v.getRisposta().getId()==id) {
				risposta.togliVoto(v);
				risposta.getUtentiVotanti().remove(utente);
				voti.remove(v);
				votoService.rimuovi(v);
			}
		}
			
				
		utenteService.inserisci(utente);
		
		model.addAttribute("domanda", risposta.getDomanda());
		model.addAttribute("utente", utente);
		
		return "domanda";
	}
	
	@PostMapping("/{idRisposta}/cambiaVoto")
	public String cambiaVoto(Model model,@PathVariable("idRisposta") Long id) {
		Risposta risposta=rispostaService.getById(id);
		
		Credentials credentials=sessionData.getCredentials();
		credentials=credentialsService.getByEmail(credentials.getEmail());
		Utente utente=credentials.getUtente();
		List<Voto> voti=utente.getVoti();
		for(Voto v:voti)
			if(v.getRisposta().getId()==id) {
				risposta.cambiaVoto(v);
				v.setUtile(!v.isUtile());
				
			}
				
		utenteService.inserisci(utente);
		
		model.addAttribute("domanda", risposta.getDomanda());
		model.addAttribute("utente", utente);
		
		return "domanda";
	}
	
	@GetMapping("/cancellaRisposta/{id}")
	public String cancellaRisposta(@PathVariable("id") Long id,Model model) {
		Credentials credentials=sessionData.getCredentials();
		credentials=credentialsService.getByEmail(credentials.getEmail());
		Utente utente=credentials.getUtente();
		List<Risposta> risposte=utente.getRisposte();
		Risposta risposta=rispostaService.getById(id);
		
		if(risposte.contains(risposta)) {
			risposte.remove(risposta);
			rispostaService.cancella(risposta);
			utente.setRisposte(risposte);
			utenteService.inserisci(utente);
		}
			
		
		
		
    	model.addAttribute("risposte", risposte);
    	
    	return "risposte";
	}
	
}
