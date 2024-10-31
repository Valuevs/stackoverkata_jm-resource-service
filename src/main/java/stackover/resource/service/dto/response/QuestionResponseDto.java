package stackover.resource.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import stackover.resource.service.entity.question.VoteTypeQuestion;

import java.time.LocalDateTime;
import java.util.List;

public record QuestionResponseDto(
        @Schema(description = "id вопроса")
        Long id,

        @Schema(description = "заголовок вопроса")
        String title,

        @Schema(description = "id автора")
        Long authorId,

        @Schema(description = "имя автора")
        String authorName,

        @Schema(description = "ссылка на изображение автора")
        String authorImage,

        @Schema(description = "описание вопроса")
        String description,

        @Schema(description = "количество просмотров")
        Long viewCount,

        @Schema(description = "репутация автора")
        Long authorReputation,

        @Schema(description = "количество ответов на вопрос")
        Long countAnswer,

        @Schema(description = "рейтинг вопроса")
        Long countValuable,

        @Schema(description = "дата создания вопроса")
        LocalDateTime persistDateTime,

        @Schema(description = "дата последнего обновления")
        LocalDateTime lastUpdateDateTime,

        @Schema(description = "кол-во голосов за вопрос")
        Long countVote,

        @Schema(description = "голос авторизованного пользователя за вопрос")
        VoteTypeQuestion voteTypeQuestion,

        @Schema(description = "список тэгов")
        List<TagResponseDto> listTagDto
) {
        public QuestionResponseDto(Long id, String title, Long authorId, String authorName, String description) {
                this(
                        id,
                        title,
                        authorId,
                        authorName,
                        null,
                        description,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                        );
        }
}
