package berlin.myboard.service;

import berlin.myboard.domain.Member;
import berlin.myboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * db에서 회원정보를 받아서 스프링부트에 전달
 */
@Component
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

//    public MyUserDetailsService(MemberService memberService) {
//        this.memberService = memberService;
//    }

    @Override
    public UserDetails loadUserByUsername(String insertedUsername) throws UsernameNotFoundException {
        Optional<Member> findOne = memberRepository.findByUsername(insertedUsername);
        Member member = findOne.orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다 ㅠ"));

        return User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
