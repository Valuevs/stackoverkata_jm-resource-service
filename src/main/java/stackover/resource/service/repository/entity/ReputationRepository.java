package stackover.resource.service.repository.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import stackover.resource.service.entity.user.reputation.Reputation;

import java.util.Optional;

public interface ReputationRepository extends JpaRepository<Reputation, Long> {

    Optional<Reputation> findByAuthor_IdAndQuestion_Id(Long authorId, Long questionId);

    Reputation findByAnswerId (long answerId);
}
