package spring.jwProject.web.member;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.jwProject.domain.member.Member;
import spring.jwProject.sevice.MemberService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService service;


    // 로그인 페이지
    @GetMapping("/login")
    public String loginPage(@ModelAttribute("member") Member member) {
        return "member/login";
    }

    // 로그인
    @PostMapping("/login")
    public String login(@ModelAttribute("member") Member member) {
        Member login = service.login(member.getMemberId(), member.getPassword());
        log.info("login member={}",login);
        if (login == null) {
            return "member/login";
        }
        return "home/home";
    }

    //회원가입 페이지
    @GetMapping("/signup")
    public String signUpPage(@ModelAttribute("member") Member member) {
        return "member/signup";
    }

    //회원가입
    @PostMapping("/signup")
    public String signUp(@ModelAttribute("member") Member member) {

        Member signUpMember = service.signUp(member);
        log.info("signUp member={}",member);

        return "member/login";
    }
}
