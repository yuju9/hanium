package berlin.myboard.market.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name="market")
public class Market {

    @Id
    @Column(nullable = false)
    private Long marketIdx;

    @Column(length = 50)
    private String itemName;

    @Column(length = 50, nullable = false)
    private String category;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String detail;

    @Column(length = 50, nullable = false)
    private double interest;

    @CreationTimestamp
    private Timestamp createDate;

    @Column
    private Timestamp repaymentDate; // 차기 상환일

    @Column
    private Timestamp maturityDate; // 만기일

    @Column(nullable = false)
    private String status;



}
