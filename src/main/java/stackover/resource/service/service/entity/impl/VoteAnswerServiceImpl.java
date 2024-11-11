package stackover.resource.service.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stackover.resource.service.entity.question.answer.VoteAnswer;
import stackover.resource.service.entity.question.answer.VoteTypeAnswer;
import stackover.resource.service.repository.entity.VoteAnswerRepository;
import stackover.resource.service.service.entity.VoteAnswerService;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VoteAnswerServiceImpl implements VoteAnswerService {

    private final VoteAnswerRepository voteAnswerRepository;

    @Override
    public VoteAnswer saveVoteAnswer(VoteAnswer voteAnswer) {
        return voteAnswerRepository.save(voteAnswer);
    }

    @Override
    public Optional<VoteAnswer> findByUserAndAnswer(Long userId, Long answerId) {
        return voteAnswerRepository.findByUserIdAndAnswerId(userId, answerId);
    }

    @Override
    public long countDownVotesByAnswerId(Long answerId) {
        return voteAnswerRepository.countByAnswerIdAndVoteType(answerId, VoteTypeAnswer.DOWN);

    }
}
