package se.ifmo.blos.lab1.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ifmo.blos.lab1.domains.User;
import se.ifmo.blos.lab1.dtos.AuthorizationDto;
import se.ifmo.blos.lab1.utils.JwtUtil;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthorizationService {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  @Transactional(readOnly = true)
  public AuthorizationDto authorize(final AuthorizationDto authorizationDto) {
    final Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authorizationDto.getUsername(), authorizationDto.getPassword()));
    final String token = jwtUtil.generateJwtToken(authentication);
    authorizationDto.setId(((User) authentication.getPrincipal()).getId());
    authorizationDto.setToken(token);
    authorizationDto.setExpiresAt(jwtUtil.getExpirationDate(token).toInstant());
    return authorizationDto;
  }
}
