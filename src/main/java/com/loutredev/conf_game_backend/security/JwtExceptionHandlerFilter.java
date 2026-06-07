package com.loutredev.conf_game_backend.security;

import com.loutredev.conf_game_backend.exceptions.jwt.JwtValidationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (JwtValidationException e) {
      handleJwtError(response, HttpStatus.UNAUTHORIZED, e.getMessage());
    } catch (Exception e) {
      handleJwtError(response, HttpStatus.INTERNAL_SERVER_ERROR, "Erreur interne d’authentification");
    }
  }

  private void handleJwtError(HttpServletResponse response, HttpStatus status, String message) throws IOException {
    response.setStatus(status.value());
    response.setContentType("application/json");
    response.getWriter().write("{\"error\": \"" + message + "\"}");
  }
}
