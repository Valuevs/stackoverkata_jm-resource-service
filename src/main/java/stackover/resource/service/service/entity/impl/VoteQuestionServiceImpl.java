package stackover.resource.service.service.entity.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stackover.resource.service.entity.question.Question;
import stackover.resource.service.entity.question.VoteQuestion;
import stackover.resource.service.entity.question.VoteTypeQuestion;
import stackover.resource.service.entity.user.User;
import stackover.resource.service.repository.entity.VoteQuestionRepository;
import stackover.resource.service.service.entity.ReputationService;
import stackover.resource.service.service.entity.VoteQuestionService;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class VoteQuestionServiceImpl extends AbstractServiceImpl<VoteQuestion, Long> implements VoteQuestionService {

    private final VoteQuestionRepository voteQuestionRepository;

    private final ReputationService reputationService;

    public VoteQuestionServiceImpl(VoteQuestionRepository voteQuestionRepository,
                                   ReputationService reputationService) {
        super(voteQuestionRepository);
        this.voteQuestionRepository = voteQuestionRepository;
        this.reputationService = reputationService;
    }

    @Override
    @Transactional
    public long setDownVoteToQuestionByUser(Question question, User user) {
        reputationService.setDownReputationToQuestion(user, question);

        VoteQuestion voteQuestion = findByUserAndQuestion(user, question).orElse(null);
        if (voteQuestion == null) {
            voteQuestion = new VoteQuestion();
            voteQuestion.setQuestion(question);
            voteQuestion.setUser(user);
            voteQuestion.setVoteTypeQuestion(VoteTypeQuestion.DOWN);
            voteQuestionRepository.save(voteQuestion);
        } else {
            voteQuestion.setVoteTypeQuestion(VoteTypeQuestion.DOWN);
            voteQuestionRepository.saveAndFlush(voteQuestion);
        }

        return countSumOfDownVoteOnQuestion(question);
    }

    private long countSumOfDownVoteOnQuestion(Question question) {
        List<VoteQuestion> allVoteQuestions = voteQuestionRepository.findAllByQuestion(question);
        long downVoteSum = allVoteQuestions
                .stream()
                .filter(voteQuestion -> voteQuestion.getVoteTypeQuestion() == VoteTypeQuestion.DOWN)
                .count();
        long upVoteSum = allVoteQuestions
                .stream()
                .filter(voteQuestion -> voteQuestion.getVoteTypeQuestion() == VoteTypeQuestion.UP)
                .count();
        return upVoteSum - downVoteSum;
    }

    @Override
    public Optional<VoteQuestion> findByUserAndQuestion(User user, Question question) {
        return voteQuestionRepository.findByUserAndQuestion(user, question);
    }

    @Override
    public long calculateVoteSum(Question question) {
        long upVotes = voteQuestionRepository.getCountUpVotes(question);
        long downVotes = voteQuestionRepository.getCountDownVotes(question);
        return upVotes - downVotes;
    }


    @Override
    public Optional<VoteQuestion> findVoteQuestion(Question question, User sender) {
        return voteQuestionRepository.findByUserAndQuestion(sender,question);
    }

    // Если пользователь еще не голосовал <за> вопрос, то создается нововое VoteQuestion за вопрос с VoteTypeQuestion.UP;
    // Если пользователь уже проголосовал <за> вопрос, то при повторном голосовании VoteQuestion остается тем же;
    // Если пользователь уже проголосовал <против>, то VoteQuestion меняет значение VoteTypeQuestion.DOWN -> VoteTypeQuestion.UP;
    @Transactional
    public void updateOrCreateUpVoteQuestion(Question question, User sender) {
        findVoteQuestion(question, sender).ifPresentOrElse(
                voteQuestionExist -> {
                    log.info("Пользователь c ID: {}, уже голосовал за вопрос с ID: {}",sender.getId(), question.getId());
                    if (voteQuestionExist.getVoteTypeQuestion() == VoteTypeQuestion.DOWN) {
                             voteQuestionExist.setVoteTypeQuestion(VoteTypeQuestion.UP);
                             update(voteQuestionExist);
                             log.info("У VoteQuestion c ID: {}, орбновилоось значение с VoteTypeQuestion.DOWN -> VoteTypeQuestion.UP", voteQuestionExist.getId());
                    } else if (voteQuestionExist.getVoteTypeQuestion() == VoteTypeQuestion.UP) {
                            log.info("VoteQuestion c ID: {}, вернула старое значение", voteQuestionExist.getId());
                    }
                },
                () -> {
                     VoteQuestion vote = new VoteQuestion(sender, question, VoteTypeQuestion.UP);
                     save(vote);
                     log.info("Пользователь ID: {} успешно проголосовал 'за' вопрос ID: {}", sender.getId(), question.getId());
                }
        );
    }

    @Override
    @Transactional
    public long setUpVoteQuestion(Question question, User sender) {
        updateOrCreateUpVoteQuestion(question, sender);
        reputationService.updateOrCreateReputationForQuestion(sender,question);
        return calculateVoteSum(question);
    }
}
