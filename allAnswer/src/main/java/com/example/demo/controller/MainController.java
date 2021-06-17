package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.Topic;
import com.example.demo.service.DomandaService;
import com.example.demo.service.TopicService;
import com.example.demo.session.SessionData;

@Controller
public class MainController {

	@Autowired
	private DomandaService domandaService;
	

	
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
	
	@GetMapping("/admin/home")
	public String adminHome(Model model) {
		return "ADMIN";
	}
	
	
	
}
