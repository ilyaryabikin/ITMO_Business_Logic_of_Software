package se.ifmo.blos.lab1.runners;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.ifmo.blos.lab1.domains.User;
import se.ifmo.blos.lab1.repositories.UserRepository;

@Component
@Order(HIGHEST_PRECEDENCE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class UserDataGenerator implements ApplicationRunner {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  @Override
  @Transactional
  public void run(ApplicationArguments args) {
    for (final var user : getUsers()) {
      if (!userRepository.existsByEmail(user.getEmail())) {
        log.info("Saving user={}", user);
        userRepository.save(user);
      }
    }
  }

  private List<User> getUsers() {
    return List.of(
        User.builder()
            .email("user1@email.com")
            .password(passwordEncoder.encode("user1pass"))
            .phoneNumber("+79211679562")
            .build(),
        User.builder()
            .email("user2@email.com")
            .password(passwordEncoder.encode("user2pass"))
            .phoneNumber("+79057586108")
            .build(),
        User.builder()
            .email("user3@email.com")
            .password(passwordEncoder.encode("user3pass"))
            .phoneNumber("+79211679665")
            .build());
  }
}
