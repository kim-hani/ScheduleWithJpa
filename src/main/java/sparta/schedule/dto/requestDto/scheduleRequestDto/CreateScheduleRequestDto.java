package sparta.schedule.dto.requestDto.scheduleRequestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateScheduleRequestDto {

    @NotBlank(message = " 제목은 필수적으로 작성해야됩니다.")
    private final String title;

    @NotBlank(message = "내용을 작성해주세요.")
    private final String content;

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private final String email;

}
