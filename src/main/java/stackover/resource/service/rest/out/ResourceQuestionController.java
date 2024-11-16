package stackover.resource.service.rest.out;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stackover.resource.service.dto.response.QuestionCreateRequestDto;
import stackover.resource.service.dto.response.QuestionResponseDto;
import stackover.resource.service.entity.question.Question;
import stackover.resource.service.entity.user.User;
import stackover.resource.service.repository.entity.UserRepository;
import stackover.resource.service.service.dto.QuestionDtoService;
import stackover.resource.service.service.entity.QuestionService;
import stackover.resource.service.service.entity.UserService;
import stackover.resource.service.service.entity.VoteQuestionService;

import java.util.Optional;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user/question")
@Tag(name = "Question API")
public class ResourceQuestionController {
    private final QuestionDtoService questionDtoService;

    private final QuestionService questionService;

    private final UserService userService;

    private final VoteQuestionService voteQuestionService;

    //TODO убрать, когда auth сервис будет готов
    private final UserRepository userRepository;

    @Operation(summary = "Добавление вопроса пользователя",
            description = "Возвращает DTO ответа на добавленияе вопроса"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вопрос добавлен"),
            @ApiResponse(responseCode = "404", description = "Пользователь, создавший вопрос не найден")
    })
    @PostMapping
    public ResponseEntity<QuestionResponseDto> addNewQuestion(
            @Parameter(name = "accountId", description = "ID пользователя")
            @RequestParam @Positive Long accountId,
            @Parameter(name = "questionCreateDTO", description = "DTO запроса создания вопроса")
            @RequestParam @NotNull QuestionCreateRequestDto questionCreateDTO) {

        log.info("Добавление вопроса от accountId: {}", accountId);
//       TODO: раскомментировать, когда auth сервис будет реализован и убрать выбор рандомного юзера
//        if (!authServiceClient.isAccountExist(accountId)) {
//            log.info("Добавить вопрос не удалось. причина: {}", "Account not found");
//            return ResponseEntity.notFound().build();
//        }
        User user = userRepository.findById(accountId).orElse(null);
        if (user == null) {
            log.info("Добавить вопрос не удалось. причина: {}", "Account not found");
            return ResponseEntity.notFound().build();
        }

        Question question = questionService.saveNewQuestion(questionCreateDTO, user);

        return ResponseEntity.ok(questionDtoService.getQuestionDtoById(question.getId()));
    }

    @Operation(summary = "Добавление вопросу пользователя down vote",
            description = "Возвращает сумму оценок вопроса"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вопросу добавлен down vote"),
            @ApiResponse(responseCode = "404", description = "Пользователь, создавший вопрос или сам вопрос не найден")
    })
    @PostMapping("/{questionId}/downVote")
    public ResponseEntity<Long> downVoteQuestion(
            @PathVariable Long questionId,
            @RequestParam @Positive Long accountId
    ) {
        log.info("Добавление down vote вопросу от accountId: {}", accountId);
//       TODO: раскомментировать, когда auth сервис будет реализован и убрать выбор рандомного юзера
//        if (!authServiceClient.isAccountExist(accountId)) {
//            log.info("Добавить вопросу down vote не удалось. причина: {}", "Account not found");
//            return ResponseEntity.notFound().build();
//        }
        User user = userRepository.findById(accountId).orElse(null);
        if (user == null) {
            log.info("Добавить down vote вопросу не удалось. причина: {}", "Account not found");
            return ResponseEntity.notFound().build();
        }

        Question question = questionService.findById(questionId).orElse(null);
        if (question == null) {
            log.info("Добавить down vote вопросу не удалось. причина: {}", "Question not found");
            return ResponseEntity.notFound().build();
        }

        long questionVoteSum = voteQuestionService.setDownVoteToQuestionByUser(question, user);
        log.info("К вопросу с questionId: {} добавлен down vote", questionId);
        log.info("Репутация accountId: {} уменьшилась на: {}", accountId, 5);

        return ResponseEntity.ok(questionVoteSum);
    }

    @Operation(summary = "Голосование за вопрос", description = "Позволяет пользователю проголосовать 'за' конкретный вопрос.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное голосование за вопрос и возврат суммы голосов"),
            @ApiResponse(responseCode = "404", description = "Аккаунт или вопрос не найдены"),
    })
    @PostMapping("/{questionId}/upVote")
    public ResponseEntity<Long> upVoteQuestion(
            @PathVariable @Positive Long questionId,
            @RequestParam @Positive Long accountId) {

        // Логируем параметры
        log.info("Получен запрос на голос 'за' для вопроса с ID: {} от пользователя с ID: {}", questionId, accountId);

        // TODO: Удалить заглушку, когда #stackover-auth-service будет полностью реализован
        // Проверяем наличие аккаунта (заглушка для сервиса аутентификации)
        Optional<User> userOptional = userService.findByAccountId(accountId);
        if (userOptional.isEmpty()) {
            log.warn("Пользователь с ID: {} не найден", accountId);
            return ResponseEntity.notFound().build();
        }

        // Проверка, существует ли вопрос
        Optional<Question> questionOptional = questionService.findQuestionByIdIfNotCreatedBy(questionId, accountId);
        if (questionOptional.isEmpty()) {
            log.info("Вопрос с ID: {} не найден", questionId);
            return ResponseEntity.notFound().build();
        }

        Long voteSum = voteQuestionService.setUpVoteQuestion(questionOptional.get(), userOptional.get());
        log.info("Успешно обновилась репутация за вопрос ID: {}.", questionId);

        return ResponseEntity.ok(voteSum);
    }
}
