package berlin.myboard.deposit.domain;

import berlin.myboard.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name="Deposit")
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long depositIdx;

    @ManyToOne
    private Member member;


    @Column(nullable = false)
    private int price;


}
