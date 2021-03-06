package se.ifmo.blos.lab1.repositories;

import java.util.Optional;
import se.ifmo.blos.lab1.domains.User;

public interface UserRepository extends PersistableRepository<User, Long> {

    boolean existsByEmail(final String email);

    Optional<User> findByEmail(final String email);
}
