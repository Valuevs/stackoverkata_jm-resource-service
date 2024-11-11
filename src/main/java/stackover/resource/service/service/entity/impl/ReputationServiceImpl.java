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
    public Optional<Reputation> findById(Long id) {
        return reputationRepository.findById(id);
    }

    @Override
    public Reputation save(Reputation reputation) {
        return reputationRepository.save(reputation);
    }

    @Override
    public Reputation update(Reputation reputation) {
        if (reputation.getId() == null || !reputationRepository.existsById(reputation.getId())) {
            throw new IllegalArgumentException("Reputation entity must have a valid ID for update");
        }
        return reputationRepository.save(reputation);
    }

    @Override
    public void deleteById(Long id) {
        reputationRepository.deleteById(id);
    }

    public Optional<Reputation> findByAuthorIdAndQuestionId(Long authorId, Long questionId) {
        return reputationRepository.findByAuthor_IdAndQuestion_Id(authorId, questionId);
    }

    @Override
    public boolean existsById(Long id) {
        return reputationRepository.existsById(id);
    }

    @Override
    public Optional<Reputation> findBySenderAndAnswerAndType(Long userSenderId, Long answerId, ReputationType reputationType) {
        return reputationRepository.findBySenderIdAndAnswerIdAndType(userSenderId, answerId, reputationType);
    }

    @Transactional
    public void setDownReputationToQuestion(User user, Question question) {
        Reputation reputation = findByAuthorIdAndQuestionId(user.getId(), question.getId()).orElse(null);
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


