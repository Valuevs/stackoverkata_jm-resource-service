package stackover.resource.service.service.dto.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import stackover.resource.service.entity.question.answer.Answer;
import stackover.resource.service.repository.entity.AnswerRepository;

import java.util.Optional;


@Service
@AllArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public Optional<Answer> findByIdWithQuestion(Long answerId) {
        return answerRepository.findByIdWithQuestion(answerId);
    }
}
