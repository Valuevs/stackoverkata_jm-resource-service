package stackover.resource.service.service.entity.impl;

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
}
