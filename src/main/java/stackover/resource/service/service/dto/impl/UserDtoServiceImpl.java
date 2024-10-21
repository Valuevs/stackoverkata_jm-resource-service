package stackover.resource.service.service.dto.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import stackover.resource.service.dto.response.UserResponseDto;
import stackover.resource.service.feign.AuthServiceClient;
import stackover.resource.service.feign.ProfileServiceClient;
import stackover.resource.service.repository.dto.UserDtoRepository;
import stackover.resource.service.service.dto.UserDtoService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDtoServiceImpl implements UserDtoService {
    private final AuthServiceClient authServiceClient;
    private final UserDtoRepository userDtoRepository;
    private final ProfileServiceClient profileServiceClient;

    @Override
    public Optional<UserResponseDto> getUserDtoByUserId(Long userId) {
        try {
            if (authServiceClient.isAccountExist(userId)) {
                UserResponseDto userResponseDto = userDtoRepository.getUserDtoByUserId(userId);
                return Optional.ofNullable(new UserResponseDto(
                        userResponseDto.id(),
                        profileServiceClient.getProfileByAuthorization(userId).getBody().email(),
                        userResponseDto.fullName(),
                        userResponseDto.linkImage(),
                        userResponseDto.city(),
                        userResponseDto.reputation(),
                        userResponseDto.registrationDate(),
                        userResponseDto.votes(),
                        null // TODO: исправить после реализации listTop3TagDto
                ));
            }
        } catch (Exception e) {
            log.warn("Ошибка на стороне AuthServiceClient: {}", e.getMessage());
            // TODO: исправить после реализации authServiceClient
            UserResponseDto userResponseDto = userDtoRepository.getUserDtoByUserId(userId);
            return Optional.ofNullable(new UserResponseDto(
                    userResponseDto.id(),
                    profileServiceClient.getProfileByAuthorization(userId).getBody().email(),
                    userResponseDto.fullName(),
                    userResponseDto.linkImage(),
                    userResponseDto.city(),
                    userResponseDto.reputation(),
                    userResponseDto.registrationDate(),
                    userResponseDto.votes(),
                    null // TODO: исправить после реализации listTop3TagDto
            ));

        }
        return Optional.empty();
    }
}
