package stackover.resource.service.service.entity;

import stackover.resource.service.entity.question.Question;
import stackover.resource.service.entity.user.User;
import stackover.resource.service.entity.user.reputation.Reputation;
import stackover.resource.service.entity.user.reputation.ReputationType;
import java.util.Optional;

public interface ReputationService extends AbstractService<Reputation, Long> {

    Optional<Reputation> findBySenderAndAnswerAndType(Long userSenderId, Long answerId, ReputationType reputationType);

    Optional<Reputation> findByAuthorIdAndQuestionId(Long authorId, Long questionId);

    void setDownReputationToQuestion(User user, Question question);

    Reputation createInitialUpReputation(User author, User sender, Question question);

    void updateOrCreateReputationForQuestion(User sender, Question question);

    Optional<Reputation> findByQuestionIdAndSenderIdAndAuthorIdAndType(Long questionId, Long senderId, Long authorId, ReputationType type);
}
