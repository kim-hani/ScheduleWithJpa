package sparta.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import sparta.schedule.domain.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    default Member findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    Optional<Member> findByEmail(String email);

    default Member findMemberByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exist email = " + email));
    }
}
