package stackover.resource.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CommentAnswerResponseDto(
        @Schema(description = "id комментария")
        Long id,

        @Schema(description = "id ответа")
        Long answerId,

        @Schema(description = "дата редактирования")
        LocalDateTime lastRedactionDate,

        @Schema(description = "дата создания ответа")
        LocalDateTime persistDate,

        @NotNull
        @NotEmpty
        @Schema(description = "текст комментария")
        String text,

        @Schema(description = "id пользователя")
        Long userId,

        @Schema(description = "ссылка на картинку пользователя")
        String imageLink,

        @Schema(description = "репутация")
        long reputation
) {
        public CommentAnswerResponseDto(Long id, Long answerId, LocalDateTime lastRedactionDate, LocalDateTime persistDate, String text, Long userId, String imageLink, long reputation) {
                this.id = id;
                this.answerId = answerId;
                this.lastRedactionDate = lastRedactionDate;
                this.persistDate = persistDate;
                this.text = text;
                this.userId = userId;
                this.imageLink = imageLink;
                this.reputation = reputation;
        }
}
