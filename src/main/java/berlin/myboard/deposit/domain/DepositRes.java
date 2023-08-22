package berlin.myboard.deposit.domain;

import lombok.*;

@Data
@Setter
@Getter
@ToString
public class DepositRes {

    private int depositIdx;

    private int amount;
}
