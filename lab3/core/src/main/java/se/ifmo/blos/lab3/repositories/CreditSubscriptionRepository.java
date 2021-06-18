package se.ifmo.blos.lab3.repositories;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import se.ifmo.blos.lab3.domains.CreditSubscription;

@Repository("creditSubscription")
public interface CreditSubscriptionRepository
    extends PersistableRepository<CreditSubscription, Long> {

  Optional<CreditSubscription> findBySubscriberId(final Long id);
}
