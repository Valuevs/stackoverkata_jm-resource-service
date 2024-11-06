package stackover.resource.service.rest.out;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stackover.resource.service.dto.response.CommentAnswerResponseDto;
import stackover.resource.service.service.dto.impl.CommentAnswerService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import stackover.resource.service.dto.response.AnswerResponseDto;
import stackover.resource.service.feign.AuthServiceClient;
import stackover.resource.service.service.dto.AnswerDtoService;
import stackover.resource.service.service.entity.QuestionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/user/question/{questionId}/answer")
@Tag(name = "Answer API")
public class ResourceAnswerController {

    private final AnswerDtoService answerDtoService;

    private final AuthServiceClient authServiceClient;

    private final QuestionService questionService;

    private final CommentAnswerService commentAnswerService;

    @Operation(summary = "Получение списка ответов по questionId и userId",
            description = "Возвращает список ответов на вопрос по ID вопроса"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список ответов на вопрос успешно получены."),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден.")
    })
    @GetMapping
    public ResponseEntity<List<AnswerResponseDto>> getAllAnswers(
            @Parameter(name = "id", description = "ID вопроса")
            @PathVariable Long questionId,
            @Parameter(name = "id", description = "ID пользователя")
            @RequestParam Long accountId)
    {
        log.info("Получение списка ответов на questionId: {} от userId: {}", questionId, accountId);
//       TODO: раскомментировать, когда auth сервис будет реализован
//        if (!authServiceClient.isAccountExist(accountId)) {
//            log.info("Ответы не были получены. причина: {}", "Account not found");
//            return ResponseEntity.notFound().build();
//        }

        if (questionService.findByIdAndUser_AccountId(questionId, accountId).isEmpty()) {
            log.info("Ответы не были получены. Причина: questionId {} from account_id {} not found", questionId, accountId);
            return ResponseEntity.notFound().build();
        }

        log.info("Ответы были успешно получены. Вопрос: {} Пользователь: {}", questionId, accountId);
        return ResponseEntity.ok(answerDtoService.getAnswerDtoByQuestionId(questionId, accountId));
    }



    @PostMapping("/{answerId}/comment")
    @Operation(summary = "Добавление комментария к ответу", description = "Добавляет комментарий к ответу на вопрос")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий успешно добавлен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CommentAnswerResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Неверные данные запроса", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content)
    })
    public ResponseEntity<CommentAnswerResponseDto> addCommentToAnswer(
            @PathVariable Long questionId,
            @PathVariable Long answerId,
            @RequestParam @Positive Long accountId,
            @RequestBody @NotNull @NotEmpty String commentText) {

        CommentAnswerResponseDto responseDto = commentAnswerService.addCommentToAnswer(questionId, answerId, commentText, accountId);
        return ResponseEntity.ok(responseDto);
    }
}
