package stackover.resource.service.rest.out;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stackover.resource.service.dto.response.UserResponseDto;
import stackover.resource.service.service.dto.UserDtoService;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "GetUserById", description = "API который возвращает пользователя по его id")
public class ResourceUserController {

    private final UserDtoService userDtoService;

    @Operation(summary = "Получение пользователя по ID", description = "Возвращает информацию о пользователе по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о пользователе успешно получена"),
            @ApiResponse(responseCode = "404", description = "Пользователь с данным ID не найден")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserDtoByUserId(
            @Parameter(description = "ID пользователя")
            @PathVariable Long userId) {
        log.info("Получение пользователя по ID: {}", userId);
        Optional<UserResponseDto> userDto = userDtoService.getUserDtoByUserId(userId);
        if (userDto.isPresent()) {
            return ResponseEntity.ok(userDto.get());
        }
        log.warn("Пользователь с ID: {} не найден.", userId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
