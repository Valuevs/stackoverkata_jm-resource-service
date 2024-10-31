package stackover.resource.service.repository.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import stackover.resource.service.entity.question.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByName(String name);

    Optional<Tag> findByName(String name);
}
