package stackover.resource.service.repository.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stackover.resource.service.dto.response.UserResponseDto;
import stackover.resource.service.entity.user.User;

@Repository
public interface UserDtoRepository extends JpaRepository<User, Long> {
    @Query("""
            SELECT new stackover.resource.service.dto.response.UserResponseDto(
                u.accountId,
                u.fullName,
                u.imageLink,
                u.city,
                COALESCE(SUM(r.count), 0),
                u.persistDateTime,
                (COUNT(a.id) + COUNT(q.id))
            )
            FROM User u
            LEFT JOIN Reputation r ON r.author = u
            LEFT JOIN Answer a ON a.user = u
            LEFT JOIN Question q ON q.user = u
            WHERE u.accountId = :userId
            GROUP BY u
            """)
    UserResponseDto getUserDtoByUserId(@Param("userId") Long userId);
}

