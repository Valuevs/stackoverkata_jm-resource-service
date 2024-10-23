package stackover.resource.service.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stackover.resource.service.entity.question.Question;
import stackover.resource.service.repository.entity.QuestionRepository;
import stackover.resource.service.service.entity.QuestionService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    public Optional<Question> findByIdAndUser_AccountId(Long id, Long accountId) {
        return questionRepository.findByIdAndUser_AccountId(id, accountId);
    }
}
