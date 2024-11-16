package stackover.resource.service.repository.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stackover.resource.service.entity.question.Question;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findByIdAndUser_AccountId(Long id, Long accountId);

    Optional<Question> findByIdAndUser_AccountIdNot(Long id, Long accountId);
}

