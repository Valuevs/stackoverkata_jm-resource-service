package stackover.resource.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import stackover.resource.service.entity.question.answer.VoteTypeAnswer;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link stackover.resource.service.entity.question.answer.Answer}
 */
public record AnswerResponseDto(
        @Schema(description = "id ответа на вопрос") Long id,

        @Schema(description = "id пользователя") Long userId,

        @Schema(description = "id вопроса") Long questionId,

        @Schema(description = "текст ответа") @NotNull @NotEmpty @NotBlank String body,

        @Schema(description = "дата создания ответа") LocalDateTime persistDate,

        @Schema(description = "польза ответа") Boolean isHelpful,

        @Schema(description = "дата решения вопроса") LocalDateTime dateAccept,

        @Schema(description = "рейнинг ответа") Long countValuable,

        @Schema(description = "рейтинг юзера") Long countUserReputation,

        @Schema(description = "ссылка на картинку пользователя") String image,

        @Schema(description = "никнейм пользователя") String nickname,

        @Schema(description = "Количество голосов") Long countVote,

        @Schema(description = "тип голоса") VoteTypeAnswer voteTypeAnswer) implements Serializable
{

}