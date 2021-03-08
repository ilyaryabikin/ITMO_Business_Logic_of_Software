package se.ifmo.blos.lab1.filters;

import io.jsonwebtoken.JwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;
import se.ifmo.blos.lab1.utils.JwtUtil;

@Slf4j
public class JwtTokenAuthFilter extends OncePerRequestFilter {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String BEARER_TOKEN_PREFIX = "Bearer ";

  private final UserDetailsService userDetailsService;
  private final JwtUtil jwtUtil;

  public JwtTokenAuthFilter(final UserDetailsService userDetailsService, final JwtUtil jwtUtil) {
    this.userDetailsService = userDetailsService;
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(
      final HttpServletRequest request,
      final @NonNull HttpServletResponse response,
      final @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader(AUTHORIZATION_HEADER);

    if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith(BEARER_TOKEN_PREFIX)) {
      log.debug("Caught unauthorized request for URL={}", request.getRequestURL().toString());
      filterChain.doFilter(request, response);
      return;
    }

    final String jwtToken = authHeader.replace(BEARER_TOKEN_PREFIX, "");
    try {
      if (jwtUtil.isTokenValid(jwtToken)) {
        final String username = jwtUtil.getUsername(jwtToken);
        final UserDetails user = userDetailsService.loadUserByUsername(username);

        final Authentication authentication =
            new UsernamePasswordAuthenticationToken(user, null, jwtUtil.getAuthorities(jwtToken));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (JwtException e) {
      log.warn("Error handling JWT token: {}", e.getMessage());
      throw e;
    } catch (UsernameNotFoundException e) {
      log.warn("Username was not found during JWT authentication");
      throw e;
    }

    filterChain.doFilter(request, response);
  }
}
