package berlin.myboard.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER("ROLE_USER"),
//    GUEST("ROLE_GUEST"),
    ADMIN("ROLE_ADMIN");

    private final String value;
//    private String grantedAuthority;
//
//    Role(String grantedAuthority) {
//        this.grantedAuthority = grantedAuthority;
//    }
//
//    public String getGrantedAuthority() {
//        return grantedAuthority;
//    }
}
