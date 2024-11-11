package stackover.resource.service.service.entity;

import stackover.resource.service.entity.question.answer.VoteAnswer;
import java.util.Optional;

public interface VoteAnswerService  {

    VoteAnswer saveVoteAnswer(VoteAnswer voteAnswer);

    Optional<VoteAnswer> findByUserAndAnswer(Long userId, Long answerId);

    long countDownVotesByAnswerId(Long answerId);
}