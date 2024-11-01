package stackover.resource.service.repository.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import stackover.resource.service.entity.question.Question;
import stackover.resource.service.entity.user.User;

public interface QuestionsRepository extends JpaRepository<Question, Long> {
    interface UserRepository  extends JpaRepository<User, Long> {

    }
}