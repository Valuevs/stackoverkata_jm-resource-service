package stackover.resource.service.repository.entity;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stackover.resource.service.entity.user.reputation.Reputation;
import stackover.resource.service.entity.user.reputation.ReputationType;

import java.util.Optional;

@Repository
public interface ReputationRepository extends JpaRepository<Reputation, Long> {
    Optional<Reputation> findByAuthor_IdAndQuestion_Id(Long authorId, Long questionId);

    Reputation findByAnswerId (long answerId);

    Optional<Reputation> findBySenderIdAndAnswerIdAndType(Long senderId, Long answerId, ReputationType type);

    Optional<Reputation> findByQuestionIdAndSenderIdAndAuthorIdAndType(Long questionId, Long senderId, Long authorId, ReputationType type);
}

