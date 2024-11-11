package stackover.resource.service.service.entity;

import stackover.resource.service.entity.question.answer.Answer;
import java.util.Optional;

public interface AnswerServices {

    long downVoteAnswer(Long questionId,Long answerId, Long accountId);

    Optional<Answer> findAnswerById(Long id);
}
