package stackover.resource.service.service.entity;

import stackover.resource.service.entity.question.Question;
import stackover.resource.service.entity.question.VoteQuestion;
import stackover.resource.service.entity.user.User;

import java.util.Optional;

public interface VoteQuestionService extends AbstractService<VoteQuestion, Long>{

    long setDownVoteToQuestionByUser(Question question, User user);

    long calculateVoteSum(Question question);

    Optional<VoteQuestion> findByUserAndQuestion(User user, Question question);

    Optional<VoteQuestion> findVoteQuestion(Question question, User sender);

    long setUpVoteQuestion(Question question, User sender);
}
