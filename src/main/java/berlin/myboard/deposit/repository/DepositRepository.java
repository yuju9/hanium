//package berlin.pay.repository;
//
//import berlin.myboard.domain.Member;
//import berlin.pay.domain.Deposit;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.Optional;
//
//public interface DepositRepository extends JpaRepository<Deposit, Long> {
//
//    @Query("SELECT amount FROM Deposit WHERE member = :member")
//    Long findAmountByMember(@Param("member") Member member);
//
//}
