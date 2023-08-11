package berlin.myboard.domain;

import lombok.Getter;

@Getter
public class MemberResponseDto {

    private Long id;
    private String username;
//    private String password;
    private Role role;

    /* entity => dto */
    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
//        this.password = member.getPassword();
        this.role = member.getRole();
    }
}
