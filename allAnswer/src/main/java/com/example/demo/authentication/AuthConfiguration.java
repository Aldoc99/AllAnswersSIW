package com.example.demo.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.demo.service.UtenteService;

@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
    private CustomOAuth2UserService oauthUserService;
	
	@Autowired
	private OAuth2LoginSuccesHandler  oAuth2LoginSuccesHandler;
	
	@Autowired
	private UtenteService userService;
	
	/*Definiamo chi può accedere e a che cosa*/
	@Override
	public void configure(HttpSecurity http) throws Exception {

		http
		.antMatcher("/**").authorizeRequests()    //Definizione del path dove è necessaria autenticazione
		.antMatchers(new String[]{"/","/not-restricted","/css/", "/images/"}).permitAll()   //Path che non hanno bisogno di autenticazione
		.anyRequest().authenticated()    //Redirect l'utente all'oauth login
		.and()
		.oauth2Login()
			.userInfoEndpoint()
			.userService(oauthUserService)
			.and()
			.successHandler(oAuth2LoginSuccesHandler)
			.defaultSuccessUrl("/home")
		.and()
		.logout().permitAll();
		
		
	}
	
	

}
