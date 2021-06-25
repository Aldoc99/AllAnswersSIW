package com.example.demo.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2LoginSuccesHandler extends SimpleUrlAuthenticationSuccessHandler{

	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		CustomOAuth2User oAuth2User=(CustomOAuth2User) authentication.getPrincipal();
		this.setDefaultTargetUrl("/loginOAuthSuccess");
		System.out.println(oAuth2User.getEmail());
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
