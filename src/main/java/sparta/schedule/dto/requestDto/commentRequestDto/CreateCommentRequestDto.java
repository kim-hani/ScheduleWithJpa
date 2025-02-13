package sparta.schedule.dto.requestDto.commentRequestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateCommentRequestDto {

    @NotBlank(message = "댓글 내용을 입력하세요.")
    private final String text;
}
