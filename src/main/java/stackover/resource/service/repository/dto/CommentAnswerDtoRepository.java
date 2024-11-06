package stackover.resource.service.repository.dto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import stackover.resource.service.dto.response.CommentAnswerResponseDto;
import stackover.resource.service.entity.question.answer.CommentAnswer;

import java.util.Optional;


public interface CommentAnswerDtoRepository extends JpaRepository<CommentAnswer, Long> {

    @Query("""
            SELECT new stackover.resource.service.dto.response.CommentAnswerResponseDto(
                ca.id,
                ca.answer.id,
                ca.commentText.lastUpdateDateTime,
                ca.commentText.persistDateTime,
                ca.commentText.text,
                u.id,
                u.imageLink,
                (SELECT COALESCE(SUM(r.count), 0) FROM Reputation r WHERE r.author = u)
            )
            FROM CommentAnswer ca
            JOIN ca.answer a
            JOIN ca.commentText ct
            JOIN ct.user u
            WHERE ca.id = :commentAnswerId
            """)
    Optional<CommentAnswerResponseDto> findCommentAnswerResponseDtoById(@Param("commentAnswerId") Long commentAnswerId);



}


