package spring.jwProject.web.member;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    public String loginPage(Model model) {
        model.addAttribute("member", new Member());
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
    public String login(@ModelAttribute("member") Member member,
                        BindingResult bindingResult) {

        Member login = service.login(member.getMemberId(), member.getPassword());
        log.info("login member={}",login);

        if (login == null) {
            bindingResult.reject("loginError", null);
        }

        if (bindingResult.hasErrors()) {
            log.info("login Fail");
            return "member/login";
        }

        return "home/home";
    }

    //회원가입 페이지
    @GetMapping("/signup")
    public String signUpPage(Model model) {
        model.addAttribute("member", new Member());
        return "member/signup";
    }

    //회원가입
    @PostMapping("/signup")
    public String signUp(@Validated @ModelAttribute("member") Member member, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes, HttpServletResponse response) {


        Member findMember = repository.findById(member.memberId).orElse(null);

        if (findMember != null) {
            // 회원가입 실패인 경우
            bindingResult.reject("signUpError", null);
        }

        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "member/signup";
        }


        Member signUpMember = service.signUp(member);
        log.info("signUpMember={}",signUpMember);

        redirectAttributes.addAttribute("loginId", signUpMember.getMemberId());
        return "redirect:/login/{loginId}";
    }
}
