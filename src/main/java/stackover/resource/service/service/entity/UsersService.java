package stackover.resource.service.service.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import stackover.resource.service.entity.user.User;

public interface UsersService extends JpaRepository<User, Long> {
}