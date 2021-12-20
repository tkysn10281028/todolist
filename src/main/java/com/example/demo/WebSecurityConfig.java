package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired	
	UserDetailsService userdetailsService;
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		
		PasswordEncoder encoder = passwordEncoder();
	
		auth
		.eraseCredentials(true)
		.userDetailsService(userdetailsService)
			.passwordEncoder(encoder);
		
		

		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/activate","/signup").permitAll()
		.antMatchers("/error").permitAll()
		.antMatchers("/admin").hasAuthority("ADMIN")
		.anyRequest().authenticated();
	
		http.formLogin()
		.loginProcessingUrl("/")
		.loginPage("/")
		.failureUrl("/?error")
		.usernameParameter("emailaddress")
		.passwordParameter("password")
		.defaultSuccessUrl("/tweet", true);
		
		http.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.deleteCookies("JSESSIONID", "CSRF-TOKEN")
		.invalidateHttpSession(true)
		.logoutSuccessUrl("/home");

	}
	
	
	
	
	
	
}
