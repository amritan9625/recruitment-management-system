package com.amritan.backend.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAccessDeniedHandler implements AccessDeniedHandler{
	
	// insufficient roles
	@Override
	public void handle(
			HttpServletRequest request,
			HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException{
		
		response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        String body = """
                {
                    "success": false,
                    "message": "Access denied",
                    "data": null,
                    "timestamp": "%s"
                }
                """.formatted(LocalDateTime.now());

        response.getWriter().write(body);
	}

}
