package com.amritan.backend.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
	
	private final JwtEncoder jwtEncoder;
	private final JwtDecoder jwtDecoder;
	
	public JwtService(@Value("${jwt.secret}") String secret) {
		
		SecretKey secretKey = new SecretKeySpec(
				secret.getBytes(), 
				"HmacSHA256" );
		
		this.jwtEncoder = NimbusJwtEncoder
				.withSecretKey(secretKey)
				.algorithm(MacAlgorithm.HS256)
				.build();
		
		this.jwtDecoder = NimbusJwtDecoder
				.withSecretKey(secretKey)
				.macAlgorithm(MacAlgorithm.HS256)
				.build();
	}
	
	public String generateToken(String email) {
		Instant now = Instant.now();
		
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.subject(email)
				.issuedAt(now)
				.expiresAt(now.plus(1, ChronoUnit.HOURS))
				.build();
		
		JwtEncoderParameters parameters = JwtEncoderParameters
				.from(JwsHeader.with(MacAlgorithm.HS256)
				.build(),
			claims);
		
		Jwt jwt = jwtEncoder.encode(parameters);
		
		return jwt.getTokenValue();
	}
	
	public String extractEmail(String token) {
		Jwt jwt = jwtDecoder.decode(token);
		
		return jwt.getSubject();
	}
	
}
