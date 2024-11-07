package stackover.resource.service.service.entity.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stackover.resource.service.entity.question.Question;
import stackover.resource.service.entity.user.User;
import stackover.resource.service.entity.user.reputation.Reputation;
import stackover.resource.service.entity.user.reputation.ReputationType;
import stackover.resource.service.repository.entity.ReputationRepository;
import stackover.resource.service.service.entity.ReputationService;

import java.util.Optional;

@Service
public class ReputationServiceImpl extends AbstractServiceImpl<Reputation, Long> implements ReputationService {

    private final ReputationRepository reputationRepository;

    public ReputationServiceImpl(ReputationRepository reputationRepository) {
        super(reputationRepository);
        this.reputationRepository = reputationRepository;
    }

    @Override
    public Optional<Reputation> findByAuthorIdAndQuestionId(Long authorId, Long questionId) {
        return reputationRepository.findByAuthor_IdAndQuestion_Id(authorId, questionId);
    }

    @Override
    @Transactional
    public void setDownReputationToQuestion(User user, Question question) {
        Reputation reputation = findByAuthorIdAndQuestionId(
                user.getId(), question.getId()).orElse(null);
        if (reputation == null) {
            reputation = new Reputation();
            reputation.setType(ReputationType.VOTE_QUESTION);
            reputation.setQuestion(question);
            reputation.setAuthor(user);
            reputation.setCount(-5);
            reputationRepository.save(reputation);
        } else {
            reputation.setCount(-5);
            reputationRepository.saveAndFlush(reputation);
        }
    }
}
