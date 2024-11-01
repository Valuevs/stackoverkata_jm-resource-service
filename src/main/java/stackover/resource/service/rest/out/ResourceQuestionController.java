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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stackover.resource.service.dto.response.QuestionCreateRequestDto;
import stackover.resource.service.dto.response.QuestionResponseDto;
import stackover.resource.service.entity.question.Question;
import stackover.resource.service.entity.user.User;
import stackover.resource.service.feign.AuthServiceClient;
import stackover.resource.service.repository.entity.UserRepository;
import stackover.resource.service.service.dto.QuestionDtoService;
import stackover.resource.service.service.entity.QuestionService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user/question")
@Tag(name = "Question API")
public class ResourceQuestionController {

    private final AuthServiceClient authServiceClient;

    private final QuestionDtoService questionDtoService;

    private final QuestionService questionService;

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
}