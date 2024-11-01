package stackover.resource.service.repository.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import stackover.resource.service.entity.user.reputation.Reputation;

public interface ReputationRepository extends JpaRepository<Reputation, Long> {
    public Reputation findByAnswerId (long answerId);
}
