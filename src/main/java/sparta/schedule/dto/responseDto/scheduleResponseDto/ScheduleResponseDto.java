package sparta.schedule.dto.responseDto.scheduleResponseDto;

import lombok.Getter;
import sparta.schedule.domain.schedule.Schedule;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private final Long id;

    private final String title;

    private final String content;

    private final String username;

    private final Long commentCount;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;


    public ScheduleResponseDto(Long id, String title, String content, String username, Long commentCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.username = username;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ScheduleResponseDto toDto(Schedule schedule, Long commentCount) {
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getMember().getUsername(),
                commentCount,
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }


}
