package com.amritan.backend.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.amritan.backend.security.CustomAccessDeniedHandler;
import com.amritan.backend.security.CustomAuthenticationEntryPoint;
import com.amritan.backend.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
			.csrf(csrf -> csrf.disable())
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.sessionManagement(session ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
			.authorizeHttpRequests(auth -> auth
					// Swagger/OpenApi
					.requestMatchers( "/swagger-ui/**", "/v3/api-docs/**").permitAll()
					
					// Auth
					.requestMatchers("/api/auth/**").permitAll()
					
					// Users					
					.requestMatchers("/api/users/**").hasRole("ADMIN")
					
					// Roles
					.requestMatchers("/api/roles/**").hasRole("ADMIN")
					
					// Jobs
					.requestMatchers(HttpMethod.GET, "/api/jobs/**")
					.hasAnyRole("ADMIN", "RECRUITER", "INTERVIEWER", "CANDIDATE")
					
					.requestMatchers("/api/jobs/**")
	                .hasAnyRole("ADMIN", "RECRUITER")
					
	                // Candidates
	                .requestMatchers("/api/candidates/**")
					.hasAnyRole("ADMIN", "RECRUITER", "INTERVIEWER")
					
					// Applications
					.requestMatchers("/api/applications/**")
					.hasAnyRole("ADMIN", "RECRUITER", "INTERVIEWER", "CANDIDATE")
					
					// Interviews
					.requestMatchers("/api/interviews/**")
					.hasAnyRole("ADMIN", "RECRUITER", "INTERVIEWER", "CANDIDATE")
					
					// Offers
					.requestMatchers("/api/offers/**")
					.hasAnyRole("ADMIN", "RECRUITER", "CANDIDATE")
					
	                
					.anyRequest().authenticated()
				)
			.exceptionHandling(exception -> exception
		            .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
		            .accessDeniedHandler(new CustomAccessDeniedHandler())
		        )
			.addFilterBefore(
					jwtAuthenticationFilter, 
					UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();

	    configuration.setAllowedOrigins(List.of("http://localhost:5173"));

	    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

	    configuration.setAllowedHeaders(List.of("*"));

	    configuration.setAllowCredentials(true);

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

	    source.registerCorsConfiguration("/**", configuration);

	    return source;
	}
}
