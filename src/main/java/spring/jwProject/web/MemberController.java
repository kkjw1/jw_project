package spring.jwProject.web;

import jakarta.servlet.http.HttpServletRequest;
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
import spring.jwProject.validation.form.CheckPWMember;
import spring.jwProject.validation.form.LoginMember;
import spring.jwProject.validation.form.SignUpMember;
import spring.jwProject.validation.form.UpdateMember;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService service;
    private final MemberRepository repository;
    // 로그인 페이지
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
            log.info("login fail");
            return "member/login";
        }

        Member login = service.login(member.getId(), member.getPassword());
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
        model.addAttribute("member", new SignUpMember());
        return "member/signup";
    }

    //아이디 중복 체크
    @GetMapping("/signup/checkId")
    @ResponseBody
    public boolean checkDuplicateId(@RequestParam("id") String id) {
        return service.checkId(id);
    }

    //회원가입
    @PostMapping("/signup")
    public String signUp(@Validated @ModelAttribute("member") SignUpMember signUpMember, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "member/signup";
        }

        Member signedMember = service.signUp(new Member(
                signUpMember.getId(),
                signUpMember.getEmail(),
                signUpMember.getPassword(),
                signUpMember.getName(),
                signUpMember.getGender(),
                signUpMember.getTelecom(),
                signUpMember.getPhoneNumber(),
                signUpMember.getPostcode(),
                signUpMember.getRoadAddress(),
                signUpMember.getDetailAddress()));

        log.info("signUp complete, Member={}",signedMember);
        redirectAttributes.addAttribute("loginId", signedMember.getId());
        return "redirect:/member/login/{loginId}";
    }


    //개인정보수정 비밀번호 체크
    @GetMapping("/mypage/memberModifyCheckPW")
    public String memberModifyCheckPWForm(@RequestParam("memberId") String memberId, Model model) {
        CheckPWMember checkPWMember = new CheckPWMember();
        checkPWMember.setId(memberId);
        model.addAttribute("checkPWMember", checkPWMember);
        return "member/member_modify_checkPW";
    }

    @PostMapping("/mypage/memberModifyCheckPW")
    public String memberModifyCheckPW(@Validated @ModelAttribute CheckPWMember checkPWMember, BindingResult bindingResult,
                                      Model model, RedirectAttributes redirectAttributes, @RequestParam("memberId") String memberId) {

        if (bindingResult.hasErrors()) {
            return "member/member_modify_checkPW";
        }

        System.out.println("!!!!!!!!" + checkPWMember.getId() + checkPWMember.getPassword());
        checkPWMember.setId(memberId);
        Member checkMember = service.login(checkPWMember.getId(), checkPWMember.getPassword());

        if (checkMember == null) {
            log.info("memberModifyCheckPW Error");
            bindingResult.reject("CheckPWError", "비밀번호 체크 에러");
            return "member/member_modify_checkPW";
        }

        redirectAttributes.addAttribute("memberId", checkPWMember.getId());
        return "redirect:/member/mypage/memberModify";
    }


    //개인정보수정
    @GetMapping("/mypage/memberModify")
    public String memberModifyForm(@RequestParam("memberId") String memberId, Model model) {
        Member member = repository.findById(memberId);
        UpdateMember updateMember = new UpdateMember(member.getId(), member.getName(), member.getEmail(), member.getPassword(), member.getTelecom(), member.getPhoneNumber(), member.getGender());

        model.addAttribute("member", updateMember);
        return "member/member_modify";
    }

/*
    @PostMapping("/mypage/memberModify")
    public String myPageUpdate(@Validated @ModelAttribute("member") UpdateMember updateMember, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("myPageUpdate Fail={}", bindingResult);
            return "member/memberModify";
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
