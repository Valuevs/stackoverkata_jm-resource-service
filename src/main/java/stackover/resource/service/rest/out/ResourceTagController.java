package stackover.resource.service.rest.out;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stackover.resource.service.dto.response.TagResponseDto;
import stackover.resource.service.entity.user.reputation.ReputationType;
import stackover.resource.service.service.entity.TagService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/tag")
@Tag(name = "Resource Tag Controller", description = "API для работы с тэгами пользователя")
public class ResourceTagController {

    private final TagService tagService;

    @GetMapping("/top-3tags")
    @Operation(summary = "Получение топ-3 тэгов пользователя", description = "Получает топ-3 тэгов пользователя, в которых он заработал больше всего репутации, голосуя за/против ответов на вопросы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Тэги успешно получены", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TagResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Неверные данные запроса", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content)
    })
    public ResponseEntity<List<TagResponseDto>> getTop3TagsUser(@RequestParam @Positive Long accountId, ReputationType type) {
        List<TagResponseDto> tags = tagService.getTop3TagsByUserId(accountId, type);
        return ResponseEntity.ok(tags);
    }

}
