package berlin.myboard.controller;

import berlin.myboard.domain.MemberDto;
import berlin.myboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/view")
@RequiredArgsConstructor
public class ViewController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "join";
    }



    @GetMapping("/dashboard")
    public String dashboardPage(Model model) {
        return "dashboard";
    }
}
