package stackover.resource.service.service.entity;

import stackover.resource.service.entity.question.Question;

import java.util.Optional;

public interface QuestionService {

    Optional<Question> findByIdAndUser_AccountId(Long id, Long accountId);
}
