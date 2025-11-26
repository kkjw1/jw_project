package spring.jwProject.web.member;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.jwProject.domain.member.Member;
import spring.jwProject.repository.member.MemberRepository;
import spring.jwProject.sevice.MemberService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService service;
    private final MemberRepository repository;

    // 로그인 페이지
    @GetMapping("/login")
    public String loginPage(@ModelAttribute("member") Member member) {
        return "member/login";
    }

    @GetMapping("/login/{loginId}")
    public String loginPage2(@PathVariable("loginId") String loginId, Model model) {
        Member signUpMember = repository.findById(loginId).orElse(null);
        model.addAttribute("member", signUpMember);
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
    public String signUp(@ModelAttribute("member") Member member,
                         RedirectAttributes redirectAttributes, HttpServletResponse response) {

        Member signUpMember = service.signUp(member);
        log.info("signUpMember={}",signUpMember);
        if (signUpMember != null) {
            //회원가입이 완료된 경우 로그인 페이지로 redirect 보낸다.
            redirectAttributes.addAttribute("loginId", signUpMember.getMemberId());
            return "redirect:/login/{loginId}";
        }
        return "member/signup";
    }
}
