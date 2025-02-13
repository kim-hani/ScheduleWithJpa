package sparta.schedule.dto.responseDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sparta.schedule.domain.Comment;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentResponseDto {

    private final Long id;
    private final String text;
    private final String username;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getText(),
                comment.getMember().getUsername(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

}
