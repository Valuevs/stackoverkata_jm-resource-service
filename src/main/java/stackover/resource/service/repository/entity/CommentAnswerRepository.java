package stackover.resource.service.repository.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stackover.resource.service.entity.question.answer.CommentAnswer;
import java.util.Optional;

@Repository
public interface CommentAnswerRepository extends JpaRepository<CommentAnswer, Long> {
    Optional<CommentAnswer> findByAnswerId(Long commentAnswerId);
    CommentAnswer save(CommentAnswer commentAnswer);

}
