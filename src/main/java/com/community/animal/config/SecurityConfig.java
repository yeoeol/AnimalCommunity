package com.community.animal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
			.csrf(csrf -> csrf.disable());

		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/post").permitAll()
				.requestMatchers("/post/**").hasRole("USER")
				.requestMatchers("/user/logout", "/user/profile", "/user/modify/**").hasRole("USER")
				.requestMatchers("/user/**").permitAll()
			);

		http
			.formLogin(form -> form
				.loginPage("/user/login")
			);

		return http.build();
	}
}
