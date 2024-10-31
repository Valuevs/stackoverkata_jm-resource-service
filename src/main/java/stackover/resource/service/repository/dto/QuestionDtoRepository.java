package stackover.resource.service.repository.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stackover.resource.service.dto.response.QuestionResponseDto;
import stackover.resource.service.entity.question.Question;

@Repository
public interface QuestionDtoRepository extends JpaRepository<Question, Long> {

    @Query("""
            SELECT new stackover.resource.service.dto.response.QuestionResponseDto(
                q.id,
                q.title,
                u.id,
                u.nickname,
                q.description
            )
            FROM Question q
            LEFT JOIN User u ON u.id = q.user.id
            WHERE q.id = :questionId
            GROUP BY q
            """)
    QuestionResponseDto findByQuestionId(Long questionId);
}
