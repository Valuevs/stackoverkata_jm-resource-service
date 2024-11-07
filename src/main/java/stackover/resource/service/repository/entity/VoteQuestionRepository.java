package stackover.resource.service.repository.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stackover.resource.service.entity.question.Question;
import stackover.resource.service.entity.question.VoteQuestion;
import stackover.resource.service.entity.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteQuestionRepository extends JpaRepository<VoteQuestion, Long> {

    Optional<VoteQuestion> findByUserAndQuestion(User user, Question question);

    List<VoteQuestion> findAllByQuestion(Question question);
}
