package stackover.resource.service.service.entity;

import stackover.resource.service.entity.question.Question;
import stackover.resource.service.entity.user.User;
import stackover.resource.service.entity.user.reputation.Reputation;

import java.util.Optional;

public interface ReputationService extends AbstractService<Reputation, Long> {

    Optional<Reputation> findByAuthorIdAndQuestionId(Long authorId, Long questionId);

    void setDownReputationToQuestion(User user, Question question);
}
