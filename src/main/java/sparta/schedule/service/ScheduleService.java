package sparta.schedule.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sparta.schedule.domain.Member;
import sparta.schedule.domain.Schedule;
import sparta.schedule.dto.requestDto.UpdateScheduleRequestDto;
import sparta.schedule.dto.responseDto.ScheduleResponseDto;
import sparta.schedule.dto.responseDto.ScheduleWithNameResponseDto;
import sparta.schedule.repository.MemberRepository;
import sparta.schedule.repository.ScheduleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    public ScheduleResponseDto save(String title,String content,Long id){

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        Schedule schedule = new Schedule(title,content);
        schedule.setMember(findMember);

        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getMember().getUsername(),
                scheduleRepository.countCommentsByScheduleId(schedule.getId()),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );

    }

    public List<ScheduleResponseDto> findAll(){
        return scheduleRepository.findAll()
                .stream()
                .map(schedule -> ScheduleResponseDto.toDto(schedule, scheduleRepository.countCommentsByScheduleId(schedule.getId())))
                .toList();
    }

    public ScheduleWithNameResponseDto findById(Long id){
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        Member writer = schedule.getMember();

        return new ScheduleWithNameResponseDto(writer.getUsername(), schedule.getTitle(), schedule.getContent());
    }

    public ScheduleResponseDto update(Long id, UpdateScheduleRequestDto requestDto,Long userId){

        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);

        if(!schedule.getMember().getId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"게시물 수정 권한이 없습니다.");
        }

        scheduleRepository.save(schedule);

        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getMember().getUsername(),
                scheduleRepository.countCommentsByScheduleId(schedule.getId()),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }

    public void deleteById(Long id,Long userId){

        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);

        if(!schedule.getMember().getId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"게시물 삭제 권한이 없습니다.");
        }

        scheduleRepository.delete(schedule);
    }

    public Page<ScheduleResponseDto> findAllWithPaging(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Schedule> schedulesPage = scheduleRepository.findAllWithPaging(pageable);

        return schedulesPage.map(schedule -> {
            Long commentCount = scheduleRepository.countCommentsByScheduleId(schedule.getId());
            return ScheduleResponseDto.toDto(schedule, commentCount);
        });
    }

}
