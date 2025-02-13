package sparta.schedule.controller.authController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.schedule.domain.member.Member;
import sparta.schedule.dto.requestDto.authRequestDto.LoginRequestDto;
import sparta.schedule.dto.requestDto.authRequestDto.SignUpRequestDto;
import sparta.schedule.dto.responseDto.authResponseDto.LoginResponseDto;
import sparta.schedule.dto.responseDto.authResponseDto.SignUpResponseDto;
import sparta.schedule.service.memberService.MemberService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(
            @Valid @RequestBody SignUpRequestDto signUpRequestDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + " : " + error.getDefaultMessage()) // 필드명과 메시지 함께 반환
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }

        SignUpResponseDto signUpResponseDto = memberService.signUp(signUpRequestDto);
        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto loginRequestDto,
            HttpServletRequest request
    ){
        Member member = memberService.login(loginRequestDto);

        HttpSession session = request.getSession();
        session.setAttribute("USER", member.getId());

        return new ResponseEntity<>(new LoginResponseDto("로그인 성공"),HttpStatus.OK);
    }
}
