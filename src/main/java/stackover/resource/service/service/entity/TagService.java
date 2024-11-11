package stackover.resource.service.service.entity;

import org.springframework.data.repository.query.Param;
import stackover.resource.service.dto.response.TagResponseDto;
import stackover.resource.service.entity.question.Tag;
import stackover.resource.service.entity.user.reputation.ReputationType;

import java.util.List;
import java.util.Optional;

public interface TagService extends AbstractService<Tag, Long>{

    boolean existsByName(String name);

    Optional<Tag> findByName(String name);

    List<Tag> addNewTagsIfNotExist(List<TagResponseDto> tagsDto);

    List<TagResponseDto> getTop3TagsByUserId(Long userId, ReputationType reputationType);
}
