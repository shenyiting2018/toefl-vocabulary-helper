package com.yiting.toeflvoc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.yiting.toeflvoc.services.UserService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private UserService userService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
		.authorizeRequests()
		.antMatchers(
				"/resources/**", 
				"/static/**",
				"/vendors/**",
				"/js/**",
				"/css/**",
				"/production/**",
				"/pages/**",
				"/templates/**",
				"/build/**")
			.permitAll()
		.antMatchers("/admin/**").hasAnyRole("ADMIN")
		.antMatchers("/super/**").hasAnyRole("SUPER")
		.antMatchers("/user/**").hasAnyRole("USER")
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/login")
			.permitAll()
			.and()
		.logout()
			.permitAll()/*
		.and()
		.exceptionHandling().accessDeniedHandler(accessDeniedHandler)*/;
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider
	      = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userService);
	    authProvider.setPasswordEncoder(encoder());
	    return authProvider;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
		.ignoring()
		.antMatchers("/resources/**"); // #3
	}

	@Bean
	public PasswordEncoder encoder() {
	    return new CustomPasswordEncoder();
	}
}
