package sparta.schedule.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.schedule.dto.requestDto.CreateCommentRequestDto;
import sparta.schedule.dto.requestDto.UpdateCommentRequestDto;
import sparta.schedule.dto.responseDto.CommentResponseDto;
import sparta.schedule.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(
            @PathVariable("scheduleId") Long scheduleId,
            @RequestBody CreateCommentRequestDto requestDto,
            HttpServletRequest request){

        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("USER") == null){
            return new ResponseEntity<>("로그인이 필요합니다.",HttpStatus.UNAUTHORIZED);
        }

        Long userId = (Long) session.getAttribute("USER");
        CommentResponseDto commentResponseDto = commentService.save(scheduleId, userId, requestDto);

        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(
            @PathVariable("scheduleId") Long scheduleId){
        return ResponseEntity.ok(commentService.findAllByScheduleId(scheduleId));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequestDto requestDto, // ✅ DTO로 변경
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("USER") == null) {
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }

        Long userId = (Long) session.getAttribute("USER");
        return ResponseEntity.ok(commentService.update(commentId, userId, requestDto.getText()));
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId,
                                           HttpServletRequest request,
                                           @PathVariable("scheduleId") String scheduleId) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("USER") == null) {
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }

        Long userId = (Long) session.getAttribute("USER");
        commentService.delete(commentId, userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
