package stackover.resource.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import stackover.resource.service.entity.question.answer.VoteTypeAnswer;

import java.time.LocalDateTime;

public record AnswerRequestDto(
        @Schema(description = "id ответа на вопрос") Long id,

        @Schema(description = "id пользователя") Long userId,

        @Schema(description = "id вопроса") Long questionId,

        @NotEmpty @NotBlank @NotNull
        @Schema(description = "текст овтета")  String body,

        @Schema(description = "дата создания ответа") LocalDateTime persistDate,

        @Schema(description = "польза ответа") Boolean isHelpful,

        @Schema(description = "дата решения вопроса") LocalDateTime dateAcceptTime,

        @Schema(description = "рейтинг ответа") Long countValuable,

        @Schema(description = "рейтинг юзера") Long countUserReputation,

        @Schema(description = "ссылка на картинку пользователя") String image,

        @Schema(description = "никнейм пользователя") String nickname,

        @Schema(description = "количество голосов") Long countVote,

        @Schema(description = "тип голоса") VoteTypeAnswer voteType
        )
{

}
