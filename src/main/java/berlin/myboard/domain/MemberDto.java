package berlin.myboard.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

    private String username;    // 아이디

    private String password;

    private Role role;

    /* DTO -> Entity */
    public Member toEntity(PasswordEncoder passwordEncoder) {
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role.USER).
                build();
        return member;
    }
}
