package stackover.resource.service.repository.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stackover.resource.service.entity.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
