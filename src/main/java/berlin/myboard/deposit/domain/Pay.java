package berlin.myboard.deposit.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name="Pay")
public class Pay {

    @Id
    @Column(length = 200)
    private String impUid;

    @Column(length = 200)
    private String merchantUid;

    @Column(length = 50)
    private String PGName;

    @Column(length = 300)
    private String payMethod;

    @Column(length = 50, nullable = false)
    private String itemName;

    @Column(nullable = false)
    private int price;

    @Column(length = 50, nullable = false)
    private String buyerEmail;

    @Column(length = 20, nullable = false)
    private String buyerName;

    @Column(length = 20, nullable = false)
    private String buyerTel;

    @Column(length = 50)
    private String buyerAddress;

    @Column(length = 50)
    private String buyerPostNum;

}
