package stackover.resource.service.service.entity;

import stackover.resource.service.dto.response.QuestionCreateRequestDto;
import stackover.resource.service.entity.question.Question;
import stackover.resource.service.entity.user.User;

import java.util.Optional;

public interface QuestionService {

    Optional<Question> findByIdAndUser_AccountId(Long id, Long accountId);

    Optional<Question> findById(Long id);

    Question saveNewQuestion(QuestionCreateRequestDto questionCreateRequest, User user);

}
