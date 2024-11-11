package stackover.resource.service.repository.entity;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stackover.resource.service.entity.question.answer.VoteAnswer;
import stackover.resource.service.entity.question.answer.VoteTypeAnswer;

import java.util.Optional;

@Repository
public interface VoteAnswerRepository extends JpaRepository<VoteAnswer, Long> {

    @Query("SELECT COUNT(v) FROM VoteAnswer v WHERE v.answer.id = :answerId AND v.voteTypeAnswer = :voteType")
    long countByAnswerIdAndVoteType(@Param("answerId") Long answerId, @Param("voteType") VoteTypeAnswer voteType);

    Optional<VoteAnswer> findByUserIdAndAnswerId(Long userId, Long answerId);

    long countByVoteTypeAnswer(VoteTypeAnswer voteTypeAnswer);
}

