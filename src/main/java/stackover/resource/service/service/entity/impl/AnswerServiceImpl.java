package stackover.resource.service.service.entity.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import stackover.resource.service.entity.question.answer.Answer;

@Service
public class AnswerServiceImpl extends AbstractServiceImpl<Answer, Long> {

    public AnswerServiceImpl(@Qualifier("answerRepository") JpaRepository<Answer, Long> jpaRepository) {
        super(jpaRepository);
    }
}
