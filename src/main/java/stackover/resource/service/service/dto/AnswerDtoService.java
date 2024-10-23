package stackover.resource.service.service.dto;

import stackover.resource.service.dto.response.AnswerResponseDto;

import java.util.List;

public interface AnswerDtoService {

    List<AnswerResponseDto> getAnswerDtoByQuestionId(Long questionId, Long accountId);
}
