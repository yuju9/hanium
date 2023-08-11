package berlin.myboard.repository;

import berlin.myboard.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    /* oauth */
//    Optional<Member> findByEmail(String email);
}
