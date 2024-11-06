package stackover.resource.service.service.dto.impl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import stackover.resource.service.entity.user.User;
import stackover.resource.service.repository.entity.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    public Optional<User> findById(Long id) {
        // Заглушка: возвращаем любого пользователя из базы данных
        // TODO: Реализовать поиск пользователя по id
        return userRepository.findById(id);
    }
}
