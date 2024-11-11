package stackover.resource.service.service.entity.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import stackover.resource.service.entity.question.answer.Answer;
import stackover.resource.service.entity.question.answer.VoteAnswer;
import stackover.resource.service.entity.question.answer.VoteTypeAnswer;
import stackover.resource.service.entity.user.User;
import stackover.resource.service.entity.user.reputation.Reputation;
import stackover.resource.service.entity.user.reputation.ReputationType;
import stackover.resource.service.exception.AccountNotAvailableException;
import stackover.resource.service.exception.AnswerException;
import stackover.resource.service.feign.AuthServiceClient;
import stackover.resource.service.repository.entity.AnswerRepository;
import stackover.resource.service.service.entity.AnswerServices;
import stackover.resource.service.service.entity.ReputationService;
import stackover.resource.service.service.entity.UsersService;
import stackover.resource.service.service.entity.VoteAnswerService;


import java.util.Optional;

@Service
@Slf4j
public class AnswerServiceImpl extends AbstractServiceImpl<Answer, Long>  implements AnswerServices {

    private  AuthServiceClient authServiceClient;
    private AnswerRepository answerRepository;
    private UsersService usersService;
    private  VoteAnswerService voteAnswerService;
    private  ReputationService reputationService;

    public AnswerServiceImpl(@Qualifier("answerRepository") JpaRepository<Answer, Long> jpaRepository) {
        super(jpaRepository);
    }

    @Override
    public Optional<Answer> findAnswerById (Long id){
        return answerRepository.findById(id);  // Доступ к репозиторию через сервис
    }
    @Transactional
    @Override
    public long downVoteAnswer(Long questionId, Long answerId, Long accountId) {
        log.info("Инициализация downvote для вопроса {}, ответа {}, от пользователя {}", questionId, answerId, accountId);

        // Проверка наличия учетной записи
        boolean isAccountExist;
        try {
            isAccountExist = authServiceClient.isAccountExist(accountId);
        } catch (Exception e) {
            log.warn("Ошибка AuthServiceClient, accountId: {}", accountId);
            isAccountExist = usersService.findById(accountId).isPresent();
        }

        if (!isAccountExist) {
            throw new AccountNotAvailableException(String.format("Пользователь не найден: %s", accountId));
        }

        // Получение ответа через сервисный метод
        Answer answer = findAnswerById(answerId)
                .orElseThrow(() -> new AnswerException("Ответ не найден"));

        if (!answer.getQuestion().getId().equals(questionId)) {
            throw new AnswerException(String.format("Ответ %s не относится к вопросу %s", answerId, questionId));
        }

        if (answer.getUser().getId().equals(accountId)) {
            log.warn("Пользователь {} пытается проголосовать за свой собственный ответ {}", accountId, answerId);
            throw new AnswerException("Пользователь не может голосовать за свой собственный ответ");
        }

        User userSender = usersService.findById(accountId)
                .orElseThrow(() -> new AccountNotAvailableException(String.format("Пользователь не найден: %s", accountId)));
        log.debug("UserSender: {}", userSender);

        User answerAuthor = answer.getUser();
        log.debug("answerAuthor: {}", answerAuthor);

        Optional<VoteAnswer> voteAnswerOptional = voteAnswerService.findByUserAndAnswer(accountId, answerId);
        VoteAnswer voteAnswer;
        Reputation reputation;

        if (voteAnswerOptional.isEmpty()) {
            voteAnswer = new VoteAnswer(userSender, answer, VoteTypeAnswer.DOWN);
            voteAnswerService.saveVoteAnswer(voteAnswer);

            reputation = new Reputation();
            reputation.setType(ReputationType.VOTE_ANSWER);
            reputation.setAuthor(answerAuthor);
            reputation.setSender(userSender);
            reputation.setAnswer(answer);
            reputation.setCount(-5);
            reputationService.save(reputation);

            log.debug("Добавлен новый downvote: {}", voteAnswer);
            log.debug("Репутация уменьшена на 5: {}", reputation);

        } else {
            voteAnswer = voteAnswerOptional.get();
            if (voteAnswer.getVoteTypeAnswer().equals(VoteTypeAnswer.UP)) {
                voteAnswer.setVoteTypeAnswer(VoteTypeAnswer.DOWN);
                voteAnswerService.saveVoteAnswer(voteAnswer);

                Optional<Reputation> optionalReputation = reputationService.findBySenderAndAnswerAndType(accountId, answerId, ReputationType.VOTE_ANSWER);
                if (optionalReputation.isPresent()) {
                    reputation = optionalReputation.get();
                    reputation.setCount(-5);
                    reputationService.save(reputation);
                } else {
                    reputation = new Reputation();
                    reputation.setType(ReputationType.VOTE_ANSWER);
                    reputation.setAuthor(answerAuthor);
                    reputation.setSender(userSender);
                    reputation.setAnswer(answer);
                    reputation.setCount(-5);
                    reputationService.save(reputation);
                }

                log.debug("Голос изменен на downvote: {}", voteAnswer);
                log.debug("Репутация обновлена до -5: {}", reputation);
            }
        }

        long totalVotes = voteAnswerService.countDownVotesByAnswerId(answerId);
        log.info("Общее количество downvote голосов за ответ {}: {}", answerId, totalVotes);

        return totalVotes;
    }
}
