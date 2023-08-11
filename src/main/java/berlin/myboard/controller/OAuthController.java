package berlin.myboard.controller;

import berlin.myboard.service.OAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Tag(name="카카오 로그인", description = "카카오 로그인 api")
@Controller
@RequiredArgsConstructor
public class OAuthController {

    HttpSession session;

    @Autowired
    private final OAuthService oAuthService;

    @Operation(summary = "로그인", description = "파라미터")
    @GetMapping("/")
    public String index() {
        return "home";
    }

    @Operation(summary = "액세스 토큰 요청", description = "파라미터")
    @GetMapping("/oauth/kakao/callback")    // 리다이렉트
    public String kakaoCallback(@RequestParam String code) {

        // 원래 프론트에서 백으로 인가코드 전달, 현재는 백에서 인가코드 받아와서 테스트
        // 받아온 인가코드로 access token 요청, 이 token으로 카카오 서버에서 사용자 정보 받아오기 
        // -> 이 정보를 이용하여 회원가입 진행 or 이미 db에 있는 정보라면 로그인 진행
        String accessToken = oAuthService.getKakaoAccessToken(code);

        HashMap<String, Object> userInfo = oAuthService.getUserInfo(accessToken);
        System.out.println("login Controller : " + userInfo);

        // 회원가입 or 로그인

        return "home"; // 이 인가코드로 카카오 서버에 access token 요청
    }

}
