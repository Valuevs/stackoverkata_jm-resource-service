package stackover.resource.service.repository.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import stackover.resource.service.dto.response.TagResponseDto;
import stackover.resource.service.entity.question.Tag;
import stackover.resource.service.entity.user.reputation.ReputationType;

import java.util.List;

public interface TagDtoRepository extends JpaRepository<Tag,Long> {

    @Query("""
            SELECT new stackover.resource.service.dto.response.TagResponseDto(t.id, t.name, t.description)
            FROM Reputation r
            JOIN r.question q
            JOIN q.tags t
            WHERE r.author.id = :userId
            and r.type =:type 
            GROUP BY t.id, t.name, t.description
            ORDER BY SUM(r.count) DESC
            
            """)
    List<TagResponseDto> getTop3TagsByUserId(@Param("userId") Long userId, @Param("type") ReputationType type);
}



