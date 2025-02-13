package sparta.schedule.repository.commentRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.schedule.domain.comment.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByScheduleId(Long scheduleId);
}
