package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Risposta;
import com.example.demo.model.Utente;
import com.example.demo.model.Voto;
import com.example.demo.service.RispostaService;
import com.example.demo.service.UtenteService;
import com.example.demo.session.SessionData;

@Controller
public class RispostaController {

	@Autowired
	private RispostaService rispostaService;
	
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	SessionData sessionData;
	
	@GetMapping("/init")
	public String init(Model model) {
		Risposta risposta=new Risposta("Risposta di prova");
		Voto v1=new Voto(true);
		Voto v2=new Voto(false);
		Voto v3=new Voto(true);
		Voto v4=new Voto(true);
		
		List<Voto> voti=new ArrayList<>();
		voti.add(v1);
		voti.add(v2);
		voti.add(v3);
		voti.add(v4);
		
		risposta.setVoti(voti);
		
		risposta.setVotiPositivi(3);
		
		risposta.setVotiNegativi(1);
		
		Utente utente=sessionData.getUtente();
		utente=utenteService.getByEmail(utente.getEmail());
		List<Risposta> risposte=utente.getRisposte();
		risposte.add(risposta);
		utenteService.inserisci(utente);
		
		return "index";
	}
	@GetMapping("/risposte")
	public String getRisposte(Model model) {
		Utente utente=sessionData.getUtente();
		utente=utenteService.getByEmail(utente.getEmail());
		List<Risposta> risposte=utente.getRisposte();

		
		
		model.addAttribute("risposte", risposte);
		
		return "risposte";
	}
}
