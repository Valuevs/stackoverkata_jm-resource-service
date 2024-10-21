package stackover.resource.service.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;


public record ProfileResponseDto(
        @Schema(description = "id пользователя")
        Long accountId,
        @Schema(description = "почта пользователя")
        String email,
        @Schema(description = "имя пользователя")
        String fullName,
        @Schema(description = "город пользователя")
        String city,
        @Schema(description = "дата регистрации пользователя")
        LocalDateTime persistDateTime,
        @Schema(description = "ссылка на веб страницу пользователя")
        String linkSite,
        @Schema(description = "ссылка на GitHub пользователя")
        String linkGitHub,
        @Schema(description = "ссылка на страницу VK")
        String linkVk,
        @Schema(description = "информация о пользователе")
        String about,
        @Schema(description = "ссылка на изображение пользователя")
        String imageLink,
        @Schema(description = "ссылка на веб страницу пользователя")
        String nickname) {

}
