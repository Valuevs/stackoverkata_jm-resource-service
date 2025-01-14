package stackover.resource.service.service.entity.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stackover.resource.service.dto.response.TagResponseDto;
import stackover.resource.service.entity.question.Tag;
import stackover.resource.service.entity.user.reputation.ReputationType;
import stackover.resource.service.repository.dto.TagDtoRepository;
import stackover.resource.service.repository.entity.TagRepository;
import stackover.resource.service.service.entity.TagService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl extends AbstractServiceImpl<Tag, Long> implements TagService {

    private final TagRepository tagRepository;

    private final TagDtoRepository tagDtoRepository;


    public TagServiceImpl(TagRepository tagRepository, TagDtoRepository tagDtoRepository) {
        super(tagRepository);
        this.tagRepository = tagRepository;
        this.tagDtoRepository = tagDtoRepository;
    }



    @Override
    public boolean existsByName(String name) {
        return tagRepository.existsByName(name);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    @Transactional
    public List<Tag> addNewTagsIfNotExist(List<TagResponseDto> tagsDto) {
        List<Tag> tags = new ArrayList<>();
        for (TagResponseDto tagResponseDto: tagsDto) {
            if (!existsByName(tagResponseDto.name())) {
                stackover.resource.service.entity.question.Tag tag =
                        new stackover.resource.service.entity.question.Tag();
                tag.setName(tagResponseDto.name());
                tags.add(tagRepository.save(tag));
            } else {
                tags.add(findByName(tagResponseDto.name()).orElse(null));
            }
        }

        return tags;
    }

    @Override
    public  List<TagResponseDto> getTop3TagsByUserId(Long userId, ReputationType type) {
        return tagDtoRepository.getTop3TagsByUserId(userId,ReputationType.VOTE_ANSWER).stream()
                .limit(3)
                .collect(Collectors.toList());
    }
}

