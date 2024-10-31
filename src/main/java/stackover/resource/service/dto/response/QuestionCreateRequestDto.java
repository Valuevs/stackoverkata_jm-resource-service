package stackover.resource.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record QuestionCreateRequestDto(
        @NotNull
        @NotBlank
        @Schema(description = "Заголовок создаваемого вопроса")
        String title,

        @NotNull
        @NotBlank
        @Schema(description = "Описание создаваемого вопроса")
        String description,

        @NotNull
        @NotEmpty
        @Schema(description = "Теги создаваемого вопроса")
        List<TagResponseDto> tags
) {
}
