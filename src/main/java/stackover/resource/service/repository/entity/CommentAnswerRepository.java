package stackover.resource.service.repository.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import stackover.resource.service.entity.question.answer.CommentAnswer;

import java.util.Optional;

public interface CommentAnswerRepository extends JpaRepository<CommentAnswer, Long> {
    Optional<CommentAnswer> findByCommentAnswerId(Long comentAnswerId);
    CommentAnswer save(CommentAnswer commentAnswer);

}
