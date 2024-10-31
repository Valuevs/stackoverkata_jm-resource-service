package stackover.resource.service.service.dto.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import stackover.resource.service.dto.response.QuestionResponseDto;
import stackover.resource.service.repository.dto.QuestionDtoRepository;
import stackover.resource.service.service.dto.QuestionDtoService;

@Service
@AllArgsConstructor
public class QuestionDtoServiceImpl implements QuestionDtoService {

    private final QuestionDtoRepository questionDtoRepository;

    @Override
    public QuestionResponseDto getQuestionDtoById(Long id) {
        return questionDtoRepository.findByQuestionId(id);
    }
}
