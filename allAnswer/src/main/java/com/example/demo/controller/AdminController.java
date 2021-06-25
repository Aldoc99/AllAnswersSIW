package com.example.demo.controller;

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
import com.example.demo.model.Topic;
import com.example.demo.service.DomandaService;
import com.example.demo.service.RispostaService;
import com.example.demo.service.TopicService;

@Controller
public class AdminController {

	@Autowired
	private DomandaService domandaService;
	
	@Autowired
	private RispostaService rispostaService;

	@Autowired
	private TopicService topicService;

	@Autowired
	private TopicValidator topicValidator;
	
	@GetMapping("/admin/viewQuestions")
	public String viewQuestions(Model model) {
		model.addAttribute("domande", domandaService.tutti());
		return "adminDomande";
	}
	
	@GetMapping("/admin/cancellaDomanda/{id}")
	public String cancellaDomanda(@PathVariable("id") Long id,Model model) {
		Domanda domanda=domandaService.getById(id);
		domandaService.cancellaDomanda(domanda);
		
		model.addAttribute("domande", domandaService.tutti());
    	
    	return "adminDomande";
	}
	
	@GetMapping("/admin/domanda/{id}")
	public String getDomanda(@PathVariable("id") Long id,Model model) {
		model.addAttribute("domanda", domandaService.getById(id));
		return "adminDomanda";
	}
	
	@GetMapping("/admin/cancellaRisposta/{id}")
	public String cancellaRisposta(@PathVariable("id") Long id,Model model) {
		Risposta risposta = rispostaService.getById(id);
		rispostaService.cancella(risposta);
		model.addAttribute("domanda",risposta.getDomanda());
		return "adminDomanda";
	}
	
	@GetMapping("/admin/newTopic")
	public String newTopic(Model model) {
		model.addAttribute("topic", new Topic());
		return "topicForm";
	}
	
	@PostMapping("/admin/newTopic")
	public String createTopic(@ModelAttribute("topic") Topic topic,Model model,BindingResult bindingResult) {
		topicValidator.validate(topic, bindingResult);
		if(!bindingResult.hasErrors()) {
			topicService.inserisci(topic);
			model.addAttribute("topics", topicService.tutti());
			return "adminTopics";
		}
		return "topicForm";
		
	}
	
	@GetMapping("/admin/viewTopics")
	public String viewTopics(Model model) {
		model.addAttribute("topics", topicService.tutti());
		return "adminTopics";
	}
	
	@GetMapping("/admin/cancellaTopic/{id}")
	public String cancellaTopic(Model model,@PathVariable("id") Long id){
		topicService.cancella(topicService.getById(id));
		model.addAttribute("topics", topicService.tutti());
		return "adminTopics";
	}
}
