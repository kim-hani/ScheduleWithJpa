package sparta.schedule.repository.scheduleRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import sparta.schedule.domain.schedule.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    default Schedule findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exist id = " + id));
    }

    @Query("SELECT s FROM Schedule s ORDER BY s.updatedAt DESC")
    Page<Schedule> findAllWithPaging(Pageable pageable);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.schedule.id = :scheduleId")
    Long countCommentsByScheduleId(@Param("scheduleId") Long scheduleId);
}
