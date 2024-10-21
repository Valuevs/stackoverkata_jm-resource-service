package stackover.resource.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponseDto(
        @Schema(description = "id пользователя")
        Long id,

        @Schema(description = "почта пользователя")
        String email,

        @Schema(description = "имя пользователя")
        String fullName,

        @Schema(description = "ссылка на изображение пользователя")
        String linkImage,

        @Schema(description = "город пользователя")
        String city,

        @Schema(description = "репутация пользователя")
        Long reputation,

        @Schema(description = "дата регистрации пользователя")
        LocalDateTime registrationDate,

        @Schema(description = "количество голосов пользователя")
        Long votes,

        @Schema(description = "список топ-3 тэгов пользователя")
        List<TagResponseDto> listTop3TagDto
) {
    public UserResponseDto(Long id, String fullName, String linkImage, String city, long reputation, LocalDateTime registrationDate, long votes) {
        this(
                id,
                null,
                fullName,
                linkImage, city,
                reputation,
                registrationDate,
                votes,
                null
        );
    }
}
