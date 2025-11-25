package spring.jwProject.web.member;

import jakarta.servlet.annotation.WebServlet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.jwProject.domain.member.Member;
import spring.jwProject.sevice.MemberService;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService service;


    // 로그인 페이지
    @GetMapping("/login")
    public String loginPage(@ModelAttribute("member") Member member) {
        return "/login";
    }

    // 로그인
    @PostMapping("/login")
    public String login(@ModelAttribute("member") Member member) {
        System.out.println("login member = " + member);
        Member login = service.login(member.getMemberId(), member.getPassword());
        System.out.println("login = " + login);
        if (login == null) {
            return "/login";
        }
        return "/home";
    }

    //회원가입 페이지
    @GetMapping("/signup")
    public String signUpPage(@ModelAttribute("member") Member member) {
        System.out.println("MemberController.signUpPage");
        return "/signup";
    }

    //회원가입
    @PostMapping("/signup")
    public String signUp(@ModelAttribute("member") Member member) {
        service.signUp(member);
        return "/login";
    }
}
