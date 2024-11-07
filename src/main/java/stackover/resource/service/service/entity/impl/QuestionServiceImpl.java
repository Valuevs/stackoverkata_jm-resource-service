package stackover.resource.service.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stackover.resource.service.dto.response.QuestionCreateRequestDto;
import stackover.resource.service.entity.question.Question;
import stackover.resource.service.entity.user.User;
import stackover.resource.service.repository.entity.QuestionRepository;
import stackover.resource.service.service.entity.QuestionService;
import stackover.resource.service.service.entity.TagService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final TagService tagService;

    @Override
    public Optional<Question> findByIdAndUser_AccountId(Long id, Long accountId) {
        return questionRepository.findByIdAndUser_AccountId(id, accountId);
    }

    @Override
    public Question saveNewQuestion(QuestionCreateRequestDto questionCreateRequest, User user) {
        Question question = new Question();
        question.setTitle(questionCreateRequest.title());
        question.setDescription(questionCreateRequest.description());
        question.setTags(tagService.addNewTagsIfNotExist(questionCreateRequest.tags()));
        question.setUser(user);
        return questionRepository.save(question);
    }

    @Override
    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }
}
