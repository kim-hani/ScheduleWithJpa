package sparta.schedule.dto.responseDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScheduleWithNameResponseDto {

    private final String username;

    private final String title;

    private final String content;

}
