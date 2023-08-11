package berlin.myboard.domain;

import org.springframework.http.HttpMethod;

public enum SocialType {


    KAKAO(
            "kakao",
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.GET
    );

//    GOOGLE(
//            "google",
//            "https://www.googleapis.com/oauth2/v3/userinfo",
//            HttpMethod.GET
//    ),
//
//    NAVER(
//            "naver",
//            "https://openapi.naver.com/v1/nid/me",
//            HttpMethod.GET
//    );



    private String socialName;
    private String userInfoUrl; // access token을 통해 회원 정보를 조회할 url
    private HttpMethod method;  // 해당 url로 요청 보낼 때의 http method

    SocialType(String socialName, String userInfoUrl, HttpMethod method) {
        this.socialName = socialName;
        this.userInfoUrl = userInfoUrl;
        this.method = method;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getSocialName() {
        return socialName;
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }
}