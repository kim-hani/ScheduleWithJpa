package sparta.schedule.dto.responseDto.authResponseDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpResponseDto {
    private final String username;
    private final String email;
}
