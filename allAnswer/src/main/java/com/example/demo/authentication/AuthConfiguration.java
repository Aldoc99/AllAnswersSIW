package com.example.demo.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
    private CustomOAuth2UserService oauthUserService;
	
	@Autowired
	private OAuth2LoginSuccesHandler  oAuth2LoginSuccesHandler;
	

	
	/*Definiamo chi può accedere e a che cosa*/
	@Override
	public void configure(HttpSecurity http) throws Exception {

		http
		.antMatcher("/**").authorizeRequests()    //Definizione del path dove è necessaria autenticazione
		.antMatchers(new String[]{"/","/not-restricted"}).permitAll()   //Path che non hanno bisogno di autenticazione
		.anyRequest().authenticated()    //Redirect l'utente all'oauth login
		.and()
		.oauth2Login()
			.userInfoEndpoint()
			.userService(oauthUserService)
			.and()
			.successHandler(oAuth2LoginSuccesHandler)
		.and()
		.logout().permitAll();
		
		
	}
	
	

}
