package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.authentication.CustomOAuth2User;
import com.example.demo.controller.validator.CredentialsValidator;
import com.example.demo.controller.validator.OAuthValidator;
import com.example.demo.model.Credentials;
import com.example.demo.model.Utente;
import com.example.demo.service.CredentialsService;
import com.example.demo.service.DomandaService;
import com.example.demo.session.SessionData;

@Controller
public class AuthenticationController {

	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private DomandaService domandaService;
	
	@Autowired
	SessionData sessionData;

	@Autowired
	private CredentialsValidator credentialsValidator;

	@Autowired
	private OAuthValidator oAuthValidator;
	
	@GetMapping("/login")
	public String getLoginPage(Model model) {
		return "login";
	}
	
	@GetMapping("/loginForm")
	public String getLoginFormPage(Model model) {
		return "loginForm";
	}

	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("utente", new Utente());
		model.addAttribute("credentials", new Credentials());
		return "register";
	}
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("utente") Utente utente,
            @ModelAttribute("credentials") Credentials credentials,
            BindingResult credentialsBindingResult,
            Model model) {

   // valida i campi delle credenziali
   this.credentialsValidator.validate(credentials, credentialsBindingResult);

 
   if(!credentialsBindingResult.hasErrors()) {
       credentials.setUtente(utente);
       credentialsService.inserisci(credentials);
       sessionData.setCredentials(credentials);
       model.addAttribute("domande",domandaService.getRandomDomande());
       return "index";
   }
   return "register";
}
	/*URL successo per OAuth*/
	@GetMapping("/loginOAuthSuccess")
	public String getLoginInfo(Model model, OAuth2AuthenticationToken authentication) {
	    CustomOAuth2User user=(CustomOAuth2User)authentication.getPrincipal();
	    Credentials credentials=credentialsService.getByEmail(user.getEmail());
	    
	    	if(credentials==null) {  //Primo login con google
	    		credentials=new Credentials(user.getEmail());
	    		Utente utente=new Utente();
	    		credentials.setUtente(utente);
	    		credentialsService.inserisci(credentials);
	    	}
	    	sessionData.setCredentials(credentials);
	    	
	    	if(credentials.getUsername()==null) {
	    		model.addAttribute("credentials", credentials);
	    		return "usernameForm";
	    	}
	    		
	    		
	    		
	    model.addAttribute("domande",domandaService.getRandomDomande());
	    return "index";
	}
	
	@PostMapping("/registerUsername")
	public String registerUsername(@ModelAttribute("credentials") Credentials credentials,Model model,BindingResult bindingResult) {
		Credentials credenzialiSessione=sessionData.getCredentials();
		oAuthValidator.validate(credentials, bindingResult);
		if(!bindingResult.hasErrors()) {
			credenzialiSessione.setUsername(credentials.getUsername());
			credentialsService.inserisci(credenzialiSessione);
			model.addAttribute("domande",domandaService.getRandomDomande());
			return "index";
		}
		return "usernameForm";
		
	}
	/*URL successo per loginForm*/
	@GetMapping("/default")
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getByEmail(userDetails.getUsername());
    	if (credentials.getRuolo().equals(Credentials.ADMIN_ROLE)) {
            return "adminHome";
        }
    	sessionData.setCredentials(credentials);
		model.addAttribute("domande",domandaService.getRandomDomande());
        return "index";
    }
	
	@GetMapping("/init")
	public String initAdmin(Model model) {
		Credentials credentials=new Credentials("admin@admin.com", "admin", Credentials.ADMIN_ROLE);
		try {
			credentialsService.inserisci(credentials);
		}
		catch(Exception e) {
		}
		return "login";
	}
}
