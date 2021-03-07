package se.ifmo.blos.lab1.services;

import static java.lang.String.format;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ifmo.blos.lab1.domains.User;
import se.ifmo.blos.lab1.dtos.UserDto;
import se.ifmo.blos.lab1.mappers.UserMapper;
import se.ifmo.blos.lab1.repositories.UserRepository;

@Service("userService")
public class UserService extends CommonService<User, Long, UserDto> implements UserDetailsService {

  private static final String RESOURCE_NAME = "User";

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Autowired
  protected UserService(final UserRepository userRepository, final UserMapper userMapper) {
    super(userRepository, userMapper);
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  @Transactional(readOnly = true)
  public UserDto getByOwnedCarId(final UUID carId) {
    final User user =
        userRepository
            .findByCarId(carId)
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(
                        format("Owner for car with id %s was not found.", carId.toString())));
    return userMapper.mapToDto(user);
  }

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    return userRepository
        .findByEmail(username)
        .orElseThrow(
            () ->
                new UsernameNotFoundException(
                    format("Username with email %s was not found.", username)));
  }

  @Override
  public boolean isAlreadyExists(UserDto dto) {
    return userRepository.existsByEmail(dto.getEmail());
  }

  @Override
  protected String getResourceName() {
    return RESOURCE_NAME;
  }
}
