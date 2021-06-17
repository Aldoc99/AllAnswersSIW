package com.example.demo.authentication;

import static com.example.demo.model.Credentials.ADMIN_ROLE;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
    private CustomOAuth2UserService oauthUserService;
	
	@Autowired
	private OAuth2LoginSuccesHandler  oAuth2LoginSuccesHandler;
	
	@Autowired
	DataSource datasource;
	
	 /**
     * This method defines a "passwordEncoder" Bean.
     * The passwordEncoder Bean is used to encrypt and decrpyt the Credentials passwords.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	/*Definiamo chi può accedere e a che cosa*/
	@Override
	public void configure(HttpSecurity http) throws Exception {

		http
		.antMatcher("/**").authorizeRequests()    //Definizione del path dove è necessaria autenticazione
		.antMatchers(new String[]{"/","/css/**","/images/**","/loginForm","/register","/init"}).permitAll()   //Path che non hanno bisogno di autenticazione
		.antMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
        .antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
		.anyRequest().authenticated()    //Redirect l'utente all'oauth login
		.and()
		// login paragraph: qui definiamo come è gestita l'autenticazione
        // usiamo il protocollo formlogin 
        .formLogin()
        // la pagina di login si trova a /login
        // NOTA: Spring gestisce il post di login automaticamente
        .loginPage("/loginForm")
        .usernameParameter("email")
        // se il login ha successo, si viene rediretti al path /default
        .defaultSuccessUrl("/default")

        // logout paragraph: qui definiamo il logout
        .and()
		.oauth2Login()
			.userInfoEndpoint()
			.userService(oauthUserService)
			.and()
			.successHandler(oAuth2LoginSuccesHandler)
		.and()
		.logout()
		.logoutSuccessUrl("/").permitAll();
		
		
	}
	
	 /**
     * This method provides the SQL queries to get username and password.
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                //use the autowired datasource to access the saved credentials
                .dataSource(this.datasource)
                //retrieve username and role
                .authoritiesByUsernameQuery("SELECT email, ruolo FROM credentials WHERE email=?")
                //retrieve username, password and a boolean flag specifying whether the user is enabled or not (always enabled in our case)
                .usersByUsernameQuery("SELECT email, password, 1 as enabled FROM credentials WHERE email=?");
    }


	
	

}
