package stackover.resource.service.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import stackover.resource.service.dto.response.ProfileResponseDto;
import stackover.resource.service.exception.AccountExistException;

@FeignClient(name = "STACKOVER-PROFILE-SERVICE", fallbackFactory = ProfileServiceClient.ProfilesClientFallbackFactory.class)
public interface ProfileServiceClient {

    @GetMapping("/api/profile")
    ResponseEntity<ProfileResponseDto> getProfileByAuthorization(@RequestHeader(name = "account_id") Long accountId);

    @Component
    class ProfilesClientFallbackFactory implements FallbackFactory<FallbackWithFactory> {
        @Override
        public FallbackWithFactory create(Throwable cause) {
            return new FallbackWithFactory(cause.getMessage());
        }
    }

    record FallbackWithFactory(String reason) implements ProfileServiceClient {
        @Override
        public ResponseEntity<ProfileResponseDto> getProfileByAuthorization(@RequestHeader(name = "account_id") Long accountId) {
            String responseMessage = """
                    Account with id %s not found
                    """.formatted(accountId);
            throw new AccountExistException(responseMessage);
        }
    }
}