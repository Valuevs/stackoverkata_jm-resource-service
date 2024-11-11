package util;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import stackover.resource.service.entity.user.User;
import stackover.resource.service.repository.entity.UserRepository;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class SampleDataLoader {

    private final UserRepository userRepository;
    private final Faker faker;

    public SampleDataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.faker = new Faker();
    }

    public void run(String... args) throws Exception {

        List<User> users = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> {
                    User user = new User();
                    user.setFullName(faker.name().fullName());
                    user.setAccountId((long) i);
                    return user;
                })
                .toList();
        userRepository.saveAll(users);

    }
}