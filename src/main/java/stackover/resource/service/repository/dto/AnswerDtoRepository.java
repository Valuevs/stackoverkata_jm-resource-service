package stackover.resource.service.repository.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stackover.resource.service.dto.response.AnswerResponseDto;
import stackover.resource.service.entity.question.answer.Answer;

import java.util.List;

@Repository
public interface AnswerDtoRepository extends JpaRepository<Answer, Long> {

    @Query("""
            select new stackover.resource.service.dto.response.AnswerResponseDto(
                a.id, u.id, q.id, a.htmlBody,
                cast(to_timestamp(a.persistDateTime, "YYYY-MM-DD") as localdatetime),
                a.isHelpful, a.dateAcceptTime,
                sum(case when va.voteTypeAnswer = 'UP' then 1 when va.voteTypeAnswer = 'DOWN' then -1 else 0 end),
                (select sum(r.count) from Reputation as r where r.author.id = u.id),
                u.imageLink, u.nickname, count(va.id),
                case
                    when sum(case
                        when va.voteTypeAnswer = 'UP'
                        then 1 when va.voteTypeAnswer = 'DOWN' then -1 else 0 end) > 0
                    then stackover.resource.service.entity.question.answer.VoteTypeAnswer.UP
                    else stackover.resource.service.entity.question.answer.VoteTypeAnswer.DOWN
                end)
            from Answer as a
                join User as u on a.user.accountId = u.accountId
                left join VoteAnswer as va on a.id = va.answer.id
                right join Question as q on q.id = a.question.id
            where q.id = :questionId and q.user.accountId = :accountId
            group by a.id, u.id, q.id
            """)
    List<AnswerResponseDto> getAnswersDtoByQuestionId(@Param("questionId") Long questionId,
                                                      @Param("accountId") Long accountId);
}
