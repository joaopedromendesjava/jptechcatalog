package com.jptech.dscatalogbackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jptech.dscatalogbackend.resources.exceptions.ForbiddenHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	private static final String[] PUBLIC = { "/login" };

	private static final String[] OPERATOR_OR_ADMIN = { "/products/**", "/categories/**"};
	
	private static final String[] ADMIN = { "/users/**"};

	@Autowired
	private SecurityFilter securityFilter;
	
	@Bean
	public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
		return http.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)		
		.and().authorizeRequests()
		.antMatchers(PUBLIC).permitAll()
		.antMatchers(HttpMethod.GET, OPERATOR_OR_ADMIN).permitAll() 
		.antMatchers(OPERATOR_OR_ADMIN).hasAnyRole("OPERATOR", "ADMIN")
		.antMatchers(ADMIN).hasRole("ADMIN")
		.anyRequest().authenticated()
		.and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
		.exceptionHandling().accessDeniedHandler(new ForbiddenHandler())
		.and().build();
	}
		
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
