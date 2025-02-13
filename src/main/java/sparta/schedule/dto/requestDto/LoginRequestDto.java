package sparta.schedule.dto.requestDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginRequestDto {

    private final String email;
    private final String password;

}
