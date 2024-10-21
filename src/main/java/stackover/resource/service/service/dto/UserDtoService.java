package stackover.resource.service.service.dto;

import stackover.resource.service.dto.response.UserResponseDto;

import java.util.Optional;


public interface UserDtoService {
    Optional<UserResponseDto> getUserDtoByUserId(Long userId);
}
