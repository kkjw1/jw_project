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
import spring.jwProject.domain.BeforeMember;
import spring.jwProject.domain.member.Member;
import spring.jwProject.repository.member.MemberRepository;
import spring.jwProject.sevice.MemberService;
import spring.jwProject.validation.form.ChangePwMember;
import spring.jwProject.validation.form.LoginMember;
import spring.jwProject.validation.form.SignUpMember;
import spring.jwProject.validation.form.UpdateMember;
import spring.jwProject.web.SessionConst;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService service;
//    private final MemberRepository repository;

/*    // 로그인 페이지
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("member", new LoginMember());
        return "member/login";
    }

    @GetMapping("/login/{loginId}")
    public String loginForm2(@PathVariable("loginId") String loginId, Model model) {
        model.addAttribute("member", new LoginMember(loginId));
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

        BeforeMember login = service.login(member.getMemberId(), member.getPassword());
        log.info("login member={}",login);

        if (login == null) {
            bindingResult.reject("loginError", "로그인 에러메시지");
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
    public String signUpForm(Model model) {
        model.addAttribute("member", new Member());
        return "member/signup";
    }

    //회원가입
    @PostMapping("/signup")
    public String signUp(@Validated @ModelAttribute("member") SignUpMember signUpMember, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes, HttpServletResponse response) {


        BeforeMember findMember = repository.findById(signUpMember.memberId).orElse(null);

        if (findMember != null) {
            // 회원가입 실패인 경우
            bindingResult.reject("signUpError", "회원가입 실패 메시지");
        }

        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "member/signup";
        }


        BeforeMember member = new BeforeMember();
        member.setMemberId(signUpMember.getMemberId());
        member.setMemberName(signUpMember.getMemberName());
        member.setPassword(signUpMember.getPassword());

        member.setPostcode(signUpMember.getPostcode());
        member.setDetailAddress(signUpMember.getDetailAddress());
        member.setExtraAddress(signUpMember.getExtraAddress());
        member.setRoadAddress(signUpMember.getRoadAddress());
        member.setJibunAddress(signUpMember.getJibunAddress());


        BeforeMember signed = service.signUp(member);
        log.info("signUpMember={}",signed);

        redirectAttributes.addAttribute("loginId", signed.getMemberId());
        return "redirect:/member/login/{loginId}";
    }

    //마이페이지
    @GetMapping("/mypage")
    public String myPageForm(@RequestParam("memberId") String memberId, Model model) {
        BeforeMember member = repository.findById(memberId).orElse(null);
        model.addAttribute("member", member);

        return "member/mypage";
    }

    @PostMapping("/mypage")
    public String myPageUpdate(@Validated @ModelAttribute("member") UpdateMember updateMember, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("myPageUpdate Fail={}", bindingResult);
            return "member/mypage";
        }

        BeforeMember member = repository.findById(updateMember.getMemberId()).orElse(null);
        member.setMemberName(updateMember.getMemberName());

        member.setPostcode(updateMember.getPostcode());
        member.setDetailAddress(updateMember.getDetailAddress());
        member.setExtraAddress(updateMember.getExtraAddress());
        member.setRoadAddress(updateMember.getRoadAddress());
        member.setJibunAddress(updateMember.getJibunAddress());

        service.updateMember(member);

        return "redirect:/";
    }


    @GetMapping("/delete")
    public String delete(@RequestParam("memberId") String memberId, HttpServletRequest request) {
        log.info("delete member={}", memberId);
        service.withdraw(memberId);
        HttpSession session = request.getSession(false);
        session.invalidate();

        return "redirect:/";
    }

    //비밀번호 변경 페이지
    @GetMapping("/changePw")
    public String changePwForm(@RequestParam("memberId") String memberId, Model model) {
        model.addAttribute("member", new ChangePwMember());
        return "member/changePw";
    }

    //비밀번호 변경
    @PostMapping("/changePw")
    public String changePw(@RequestParam("memberId") String memberId,
                           @Validated @ModelAttribute("member") ChangePwMember changePwMember, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("changePw Error={}", bindingResult);
            return "member/changePw";
        }

        if (!changePwMember.getPassword().equals(changePwMember.getPassword2())) {
            log.info("password={} password2={}", changePwMember.getPassword(), changePwMember.getPassword2());
            bindingResult.reject("PwCrossCheckError", "비밀번호 불일치 에러메시지");
            log.info("changePw Error={}", bindingResult);
            return "member/changePw";
        }

        BeforeMember member = repository.findById(memberId).orElse(null);
        member.setPassword(changePwMember.getPassword());
        service.updateMember(member);

        return "redirect:/";
    }*/

}
