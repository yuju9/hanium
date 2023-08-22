package berlin.myboard.deposit.domain;

import berlin.myboard.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDepositRes {
    
    private String username;    // 아이디

    private String password;

    private Long deposit;

    public MemberDepositRes(Member member) {
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.deposit = member.getDeposit();
    }

    public Member toEntity() {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .deposit(deposit)
                .build();

        return member;
    }

}
