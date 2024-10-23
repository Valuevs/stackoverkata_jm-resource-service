package stackover.resource.service.service.dto.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import stackover.resource.service.dto.response.AnswerResponseDto;
import stackover.resource.service.repository.dto.AnswerDtoRepository;
import stackover.resource.service.service.dto.AnswerDtoService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerServiceDtoImpl implements AnswerDtoService {

    private final AnswerDtoRepository answerDtoRepository;

    @Override
    public List<AnswerResponseDto> getAnswerDtoByQuestionId(Long questionId, Long accountId) {
        return answerDtoRepository.getAnswersDtoByQuestionId(questionId, accountId);
    }
}
