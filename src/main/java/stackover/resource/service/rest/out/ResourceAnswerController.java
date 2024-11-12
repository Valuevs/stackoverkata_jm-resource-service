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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stackover.resource.service.converters.AnswerConverter;
import stackover.resource.service.dto.request.AnswerRequestDto;
import stackover.resource.service.dto.response.AnswerResponseDto;
import stackover.resource.service.dto.response.CommentAnswerResponseDto;
import stackover.resource.service.entity.question.answer.Answer;
import stackover.resource.service.exception.AccountExistException;
import stackover.resource.service.exception.AnswerException;
import stackover.resource.service.exception.QuestionException;
import stackover.resource.service.repository.entity.UserRepository;
import stackover.resource.service.service.dto.impl.CommentAnswerService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import stackover.resource.service.feign.AuthServiceClient;
import stackover.resource.service.service.dto.AnswerDtoService;
import stackover.resource.service.service.entity.AbstractService;
import stackover.resource.service.service.entity.AnswerServices;
import stackover.resource.service.service.entity.QuestionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/user/question/{questionId}/answer")
@Tag(name = "Answer API")
public class ResourceAnswerController {

    private final AnswerDtoService answerDtoService;

    private final AnswerServices answerServices;

    private final AuthServiceClient authServiceClient;

    private final QuestionService questionService;

    private final CommentAnswerService commentAnswerService;

    private final AnswerConverter answerConverter;

    private final AbstractService<Answer, Long> answerService;

    private final UserRepository mockAuthFeignClient;

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
            @RequestBody @NotNull @NotEmpty String commentText)
    {
        CommentAnswerResponseDto responseDto = commentAnswerService.addCommentToAnswer(questionId, answerId, commentText, accountId);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Добавление ответа", description = "Добавляет ответ к вопросу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ответ успешно добавен"),
            @ApiResponse(responseCode = "400", description = "Неправильный параметр"),
            @ApiResponse(responseCode = "404", description = "Вопрос не найден"),
            @ApiResponse(responseCode = "422", description = "Данные структурно верны, но логически некорректны")
    })
    @PostMapping
    public ResponseEntity<AnswerResponseDto> addAnswerToQuestion(
            @Parameter(name = "id", description = "ID вопроса")
            @PathVariable Long questionId,
            @Parameter(name = "id", description = "ID пользователя")
            @RequestParam @Positive Long accountId,
            @RequestBody AnswerRequestDto answerRequestDto)
    {
        log.info("Добавление ответа на questionId: {} от userId: {}", questionId, accountId);
//       TODO: раскомментировать, когда auth сервис будет реализован
//        if (!authServiceClient.isAccountExist(accountId)) {
//            log.info("Отве не был получен. причина: {}", "Account not found");
//            return ResponseEntity.badRequest().build();
//        }
        AnswerResponseDto response;
        try {
            // TODO: заглушка пока сервис не реализован.
            //  try catch конструкцию убрать, когда будет релизован RestControllerAdvice
            mockAuthFeignClient.findByAccountId(accountId).orElseThrow(() -> new AccountExistException("Account not found."));

            questionService.findById(questionId).orElseThrow(() -> new QuestionException("Question not found."));
            Answer answer = answerService.save(answerConverter.answerRequestDtoToAnswer(answerRequestDto));
            response = answerDtoService.getAnswerResponseDtoById(answer.getId()).get();
        } catch(AccountExistException e) {
            log.warn("With accountId = {}, msg: {}", accountId, e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch(QuestionException e) {
            log.warn("With questionId = {}, msg: {}", questionId, e.getMessage());
            return ResponseEntity.notFound().build();
        } catch(Exception e) {
            log.warn("Тело реквеста не валидно. msg: {}", e.getMessage());
            return ResponseEntity.unprocessableEntity().build();
        }

        log.info("Ответ был успешно добавлен. answerId: {}, questionId: {}", response.id(), questionId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/{answerId}/downVote")
    @Operation(summary = "Голосование за ответ", description = "Голосование за ответ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Общее число голосов",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Long.class))}),
            @ApiResponse(responseCode = "400", description = "Отсутствие ответа",
                    content = @Content)})
    public ResponseEntity<Long> downVoteAnswer(
            @PathVariable Long questionId,
            @PathVariable Long answerId,
            @RequestParam @Positive Long accountId) {
        log.info("Создается голосование для вопроса {}, ответа {}, пользователем {}.", questionId, answerId, accountId);
        Long totalVotingCount = answerServices.downVoteAnswer(questionId, answerId, accountId);
        log.info("Пользователь {} проголосовал за ответ {}. Общее кол-во голосов {}", accountId, answerId, totalVotingCount);
        return new ResponseEntity<>(totalVotingCount, HttpStatus.OK);
    }
}
