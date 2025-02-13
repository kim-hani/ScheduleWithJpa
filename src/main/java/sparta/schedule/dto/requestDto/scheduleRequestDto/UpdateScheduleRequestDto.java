package sparta.schedule.dto.requestDto.scheduleRequestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateScheduleRequestDto {

    @NotBlank(message = "수정할 제목을 작성해주세요.")
    private final String title;

    @NotBlank(message = "수정할 내용을 작성해주세요")
    private final String content;
}
