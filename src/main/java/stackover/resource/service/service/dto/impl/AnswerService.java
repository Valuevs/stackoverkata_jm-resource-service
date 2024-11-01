package stackover.resource.service.service.dto.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import stackover.resource.service.entity.question.answer.CommentAnswer;
import stackover.resource.service.repository.entity.CommentAnswerRepository;


@Service
@AllArgsConstructor
public class AnswerService {

    private final CommentAnswerRepository commentAnswerRepository;

    public CommentAnswer saveCommentAnswer(CommentAnswer commentAnswer) {
        return commentAnswerRepository.save(commentAnswer);
    }
}
