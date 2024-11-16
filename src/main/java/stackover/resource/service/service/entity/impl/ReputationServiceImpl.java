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

    @Override
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

    @Override
    public Reputation createInitialUpReputation(User author, User sender, Question question) {
        Reputation newReputation = new Reputation();
        newReputation.setAuthor(author);
        newReputation.setSender(sender);
        newReputation.setCount(10);
        newReputation.setType(ReputationType.VOTE_QUESTION);
        newReputation.setQuestion(question);
        return newReputation;
    }

    // Если пользователь еще не голосовал <за> вопрос, то создается новая репутация равная 10;
    // Если пользователь уже проголосовал <за> вопрос, то при повторном голосовании репутация получает значение 10;
    // Если пользователь уже проголосовал <против>, то репутация получает новое значение 10;
    @Override
    @Transactional
    public void updateOrCreateReputationForQuestion(User sender, Question question) {
        User questionAuthor = question.getUser();
        findByQuestionIdAndSenderIdAndAuthorIdAndType(question.getId(), sender.getId(), questionAuthor.getId(), ReputationType.VOTE_QUESTION).ifPresentOrElse(
                reputation -> {
                    reputation.setCount(10);
                    update(reputation);
                },
                () -> save(createInitialUpReputation(questionAuthor, sender, question))
        );
    }

    @Override
    public Optional<Reputation> findBySenderAndAnswerAndType(Long userSenderId, Long answerId, ReputationType reputationType) {
        return reputationRepository.findBySenderIdAndAnswerIdAndType(userSenderId, answerId, reputationType);
    }

    @Override
    public Optional<Reputation> findByQuestionIdAndSenderIdAndAuthorIdAndType(Long questionId, Long senderId, Long authorId, ReputationType type) {
        return reputationRepository.findByQuestionIdAndSenderIdAndAuthorIdAndType(questionId, senderId, authorId, type);
    }
}
