package sparta.schedule.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.schedule.dto.requestDto.CreateScheduleRequestDto;
import sparta.schedule.dto.requestDto.UpdateScheduleRequestDto;
import sparta.schedule.dto.responseDto.ScheduleResponseDto;
import sparta.schedule.dto.responseDto.ScheduleWithNameResponseDto;
import sparta.schedule.service.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<?> save(
            @RequestBody CreateScheduleRequestDto createScheduleRequestDto,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("USER") == null) {
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }

        Long userId = (Long) session.getAttribute("USER");

        ScheduleResponseDto scheduleResponseDto =
                scheduleService.save(
                        createScheduleRequestDto.getTitle(),
                        createScheduleRequestDto.getContent(),
                        userId);

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAll() {
        List<ScheduleResponseDto> scheduleResponseDto
                = scheduleService.findAll();

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleWithNameResponseDto> findById(@PathVariable("id") Long id) {

        ScheduleWithNameResponseDto scheduleWithNameResponseDto
                = scheduleService.findById(id);

        return new ResponseEntity<>(scheduleWithNameResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Long id,
            @RequestBody UpdateScheduleRequestDto updateScheduleRequestDto,
            HttpServletRequest request) {

        // 로그인 여부 확인
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("USER") == null) {
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }

        // 세션에서 로그인한 사용자 ID 가져오기
        Long userId = (Long) session.getAttribute("USER");

        // 게시물 수정
        ScheduleResponseDto scheduleResponseDto = scheduleService.update(id, updateScheduleRequestDto, userId);

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    // 작성자만 제거 가능
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("USER") == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        Long userId = (Long) session.getAttribute("USER");

        scheduleService.deleteById(id,userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<ScheduleResponseDto>> getSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ScheduleResponseDto> schedules = scheduleService.findAllWithPaging(page, size);
        return ResponseEntity.ok(schedules);
    }
}
