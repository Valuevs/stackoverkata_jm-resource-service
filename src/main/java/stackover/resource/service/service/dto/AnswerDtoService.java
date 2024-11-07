package stackover.resource.service.service.dto;

import stackover.resource.service.dto.response.AnswerResponseDto;

import java.util.List;
import java.util.Optional;

public interface AnswerDtoService {

    List<AnswerResponseDto> getAnswerDtoByQuestionId(Long questionId, Long accountId);

    Optional<AnswerResponseDto> getAnswerResponseDtoById(Long answerId);
}
