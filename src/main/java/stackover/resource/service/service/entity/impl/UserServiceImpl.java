package stackover.resource.service.service.entity.impl;

import org.springframework.stereotype.Service;
import stackover.resource.service.entity.user.User;
import stackover.resource.service.repository.entity.UserRepository;
import stackover.resource.service.service.entity.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl extends AbstractServiceImpl<User, Long> implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByAccountId(Long accountId) {
        return userRepository.findByAccountId(accountId);
    }

    @Override
    public Optional<User> getAnyUser() {
        return userRepository.findTopByOrderByIdAsc();
    }

}
