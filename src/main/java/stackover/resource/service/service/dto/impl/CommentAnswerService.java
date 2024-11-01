package stackover.resource.service.service.dto.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stackover.resource.service.dto.response.CommentAnswerResponseDto;
import stackover.resource.service.entity.CommentText;
import stackover.resource.service.entity.CommentType;
import stackover.resource.service.entity.question.Question;
import stackover.resource.service.entity.question.answer.Answer;
import stackover.resource.service.entity.question.answer.CommentAnswer;
import stackover.resource.service.entity.user.User;
import stackover.resource.service.repository.dto.CommentAnswerDtoRepository;
import stackover.resource.service.repository.entity.QuestionRepository;
import stackover.resource.service.repository.entity.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentAnswerService {

    private final CommentAnswerDtoRepository commentAnswerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;


    @Transactional
    public CommentAnswerResponseDto addCommentToAnswer(Long questionId, Long answerId, String text, Long accountId) {
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Вопрос не найден"));

        Answer answer = commentAnswerRepository.findByIdWithQuestion(answerId)
                .filter(a -> a.getQuestion().getId().equals(questionId))
                .orElseThrow(() -> new IllegalArgumentException("Ответ не принадлежит вопросу"));

        CommentAnswer commentAnswer = new CommentAnswer();
        commentAnswer.setAnswer(answer);
        commentAnswer.setComment((new CommentText(CommentType.ANSWER)));
        commentAnswer.getCommentText().setText(text);
        commentAnswer.getCommentText().setUser(user);
        commentAnswer.getCommentText().setPersistDateTime(LocalDateTime.now());
        commentAnswer.getCommentText().setLastUpdateDateTime(LocalDateTime.now());

        commentAnswer = commentAnswerRepository.save(commentAnswer);

        return commentAnswerRepository.findCommentAnswerResponseDtoById(commentAnswer.getId())
                .orElseThrow(() -> new IllegalArgumentException("Комментарий не найден"));
    }


}
