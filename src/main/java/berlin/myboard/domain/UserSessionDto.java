package berlin.myboard.domain;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserSessionDto implements Serializable {

    private String username;
    private String password;
    private Role role;

    /* Entity -> Dto */
    public UserSessionDto(Member member) {
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.role = member.getRole();
    }
}
