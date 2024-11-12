package stackover.resource.service.repository.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stackover.resource.service.entity.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // TODO: удалить, когда Auth client будет реализован и если не будет нужен
    Optional<User> findByAccountId(Long accountId);
}
