package sparta.schedule.service.memberService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sparta.schedule.config.encoder.PasswordEncoder;
import sparta.schedule.domain.member.Member;
import sparta.schedule.dto.requestDto.authRequestDto.LoginRequestDto;
import sparta.schedule.dto.requestDto.authRequestDto.SignUpRequestDto;
import sparta.schedule.dto.responseDto.authResponseDto.SignUpResponseDto;
import sparta.schedule.repository.memberRepository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        if(memberRepository.findByEmail(signUpRequestDto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일 입니다.");
        }

        Member member = new Member();
        member.setUsername(signUpRequestDto.getUsername());

        String encryptedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());

        member.setPassword(encryptedPassword);
        member.setEmail(signUpRequestDto.getEmail());

        memberRepository.save(member);

        return new SignUpResponseDto(member.getUsername(), member.getEmail());
    }

    public Member login(LoginRequestDto loginRequestDto) {
        Member member = memberRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일이나 비밀번호가 일치하지 않습니다."));

        // 입력한 비밀번호와 암호화된 비밀번호 비교
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일이나 비밀번호가 일치하지 않습니다.");
        }

        return member;
    }
}
