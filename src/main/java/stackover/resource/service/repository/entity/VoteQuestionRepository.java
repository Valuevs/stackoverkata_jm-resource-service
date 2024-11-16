package stackover.resource.service.repository.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stackover.resource.service.entity.question.Question;
import stackover.resource.service.entity.question.VoteQuestion;
import stackover.resource.service.entity.question.VoteTypeQuestion;
import stackover.resource.service.entity.user.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoteQuestionRepository extends JpaRepository<VoteQuestion, Long> {

    Optional<VoteQuestion> findByUserAndQuestion(User user, Question question);

    List<VoteQuestion> findAllByQuestion(Question question);

    @Query("""
    SELECT COUNT(v) 
    FROM VoteQuestion v 
    WHERE v.question = :question 
      AND v.voteTypeQuestion = 'UP'
    """)
    long getCountUpVotes(@Param("question") Question question);

    @Query("""
    SELECT COUNT(v) 
    FROM VoteQuestion v 
    WHERE v.question = :question 
      AND v.voteTypeQuestion = 'DOWN'
    """)
    long getCountDownVotes(@Param("question") Question question);


}
