package com.community.animal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.community.animal.user.domain.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 시큐리티 Role 수직적 계층 시큐리티에 적용
	@Bean
	public RoleHierarchy roleHierarchy() {
		return RoleHierarchyImpl.withRolePrefix("ROLE_")
			.role(Role.ADMIN.toString()).implies(Role.USER.toString())
			.build();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
			.csrf(csrf -> csrf.disable());

		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/posts", "/posts\\?postCategory=.*","/login").permitAll()
				.requestMatchers("/posts/**").hasRole("USER")
				.requestMatchers("/user/profile", "/user/modify/**").hasRole("USER")
				.requestMatchers("/user/**").permitAll()
			);

		http
			.formLogin(form -> form
				.loginPage("/login")
				.usernameParameter("email")
				.defaultSuccessUrl("/posts")
			);

		return http.build();
	}
}
