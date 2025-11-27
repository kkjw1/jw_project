package spring.jwProject.web.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
import spring.jwProject.validation.form.LoginMember;
import spring.jwProject.validation.form.SignUpMember;
import spring.jwProject.web.SessionConst;

import java.util.Optional;

import static spring.jwProject.web.SessionConst.LOGIN_MEMBER;

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
    public String login(@Validated @ModelAttribute("member") LoginMember member, BindingResult bindingResult,
                        HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            log.info("login Fail");
            return "member/login";
        }

        Member login = service.login(member.getMemberId(), member.getPassword());
        log.info("login member={}",login);

        if (login == null) {
            bindingResult.reject("loginError", "아이디 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해 주세요.");
            return "member/login";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, login);
        return "redirect:/";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
        return "redirect:/";
    }


    //회원가입 페이지
    @GetMapping("/signup")
    public String signUpPage(Model model) {
        model.addAttribute("member", new Member());
        return "member/signup";
    }

    //회원가입
    @PostMapping("/signup")
    public String signUp(@Validated @ModelAttribute("member") SignUpMember signUpMember, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes, HttpServletResponse response) {


        Member findMember = repository.findById(signUpMember.memberId).orElse(null);

        if (findMember != null) {
            // 회원가입 실패인 경우
            bindingResult.reject("signUpError", "회원가입 실패");
        }

        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "member/signup";
        }


        Member member = new Member(signUpMember.getMemberId(),signUpMember.getMemberName(),
                signUpMember.getPassword(), signUpMember.getAddress());

        Member signed = service.signUp(member);
        log.info("signUpMember={}",signed);

        redirectAttributes.addAttribute("loginId", signed.getMemberId());
        return "redirect:/login/{loginId}";
    }
}
