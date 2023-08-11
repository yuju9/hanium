package berlin.myboard.service;

import berlin.myboard.domain.Member;
import berlin.myboard.domain.MemberDto;
import berlin.myboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(MemberDto dto) {
        Member member = dto.toEntity(passwordEncoder);
        validateDuplicateMember(member);

        return memberRepository.save(member).getId();
    }

    // 로그인
    public boolean isValidMember(MemberDto dto) {
        Optional<Member> member = memberRepository.findByUsername(dto.getUsername());
        if (member.isPresent()) {
            return member.get().getPassword().equals(dto.getPassword());
        }
        return false;
    }

    // 회원가입시 아이디 중복 확인
    private void validateDuplicateMember(Member member) {
        memberRepository.findByUsername(member.getUsername())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다");
                });
    }
}
