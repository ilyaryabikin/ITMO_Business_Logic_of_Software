package se.ifmo.blos.lab1.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import se.ifmo.blos.lab1.domains.User;

public interface UserRepository extends PersistableRepository<User, Long> {

  boolean existsByEmail(final String email);

  Optional<User> findByEmail(final String email);

  @Query("SELECT u FROM cars c JOIN c.owner u WHERE c.id = ?1")
  Optional<User> findByCarId(final UUID carId);
}
