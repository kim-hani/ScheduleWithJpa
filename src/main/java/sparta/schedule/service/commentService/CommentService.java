package sparta.schedule.service.commentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sparta.schedule.domain.comment.Comment;
import sparta.schedule.domain.member.Member;
import sparta.schedule.domain.schedule.Schedule;
import sparta.schedule.dto.requestDto.commentRequestDto.CreateCommentRequestDto;
import sparta.schedule.dto.responseDto.commentResponseDto.CommentResponseDto;
import sparta.schedule.repository.commentRepository.CommentRepository;
import sparta.schedule.repository.memberRepository.MemberRepository;
import sparta.schedule.repository.scheduleRepository.ScheduleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    public CommentResponseDto save(Long scheduleId, Long userId, CreateCommentRequestDto requestDto){
        Member member = memberRepository.findByIdOrElseThrow(userId);

        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        Comment comment = new Comment(requestDto.getText(),member,schedule);

        commentRepository.save(comment);

        return CommentResponseDto.toDto(comment);
    }

    public List<CommentResponseDto> findAllByScheduleId(Long scheduleId){
        return commentRepository.findByScheduleId(scheduleId)
                .stream()
                .map(CommentResponseDto::toDto)
                .toList();
    }

    public CommentResponseDto update(Long commentId, Long userId, String newText) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));

        if (!comment.getMember().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "댓글 수정 권한이 없습니다.");
        }

        comment.setText(newText);
        commentRepository.save(comment);

        return CommentResponseDto.toDto(comment);
    }


    public void delete(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));

        if (!comment.getMember().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "댓글 삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }


}
