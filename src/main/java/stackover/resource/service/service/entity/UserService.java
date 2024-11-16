package stackover.resource.service.service.entity;

import stackover.resource.service.entity.user.User;
import java.util.Optional;

public interface UserService extends AbstractService<User, Long> {
    Optional<User> findByAccountId(Long accountId);
    Optional<User> getAnyUser();
}
