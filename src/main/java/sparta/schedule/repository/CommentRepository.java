package sparta.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import sparta.schedule.domain.Comment;
import sparta.schedule.domain.Member;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByScheduleId(Long scheduleId);
}
