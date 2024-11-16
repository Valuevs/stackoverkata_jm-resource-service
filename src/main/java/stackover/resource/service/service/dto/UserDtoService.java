package stackover.resource.service.service.dto;

import stackover.resource.service.dto.response.UserResponseDto;
import stackover.resource.service.entity.user.User;

import java.util.Optional;


public interface UserDtoService {
    Optional<UserResponseDto> getUserDtoByUserId(Long userId);
}
