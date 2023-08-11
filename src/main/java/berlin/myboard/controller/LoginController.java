package berlin.myboard.controller;

import berlin.myboard.domain.Member;
import berlin.myboard.domain.MemberDto;
import berlin.myboard.domain.MemberResponseDto;
import berlin.myboard.repository.MemberRepository;
import berlin.myboard.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="시큐리티", description = "회원가입, 로그인")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;


    // rest api 응답 예제
//   @PostMapping("/login")
//    public ResponseEntity<MemberResponseDto> login(MemberDto dto) {
//
//        boolean isValidMember = memberService.isValidMember(dto);
//        if (isValidMember) {
//            Member member = memberRepository.findByUsername(dto.getUsername()).get();
//            MemberResponseDto memberResponseDto = new MemberResponseDto(member);
//            return ResponseEntity.ok(memberResponseDto);
//        }
//       return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//   }

    @Operation(summary = "회원가입", description = "파라미터")
    @PostMapping("/join")
    public ResponseEntity<MemberResponseDto> join(MemberDto dto) {
        Long id = memberService.join(dto);
        Member member = memberRepository.findById(id).get();
        MemberResponseDto memberResponseDto = new MemberResponseDto(member);
        return ResponseEntity.ok(memberResponseDto);
//        return new ResponseEntity<MemberResponseDto>(memberResponseDto, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public String logout() {
        return "login";
    }

}
