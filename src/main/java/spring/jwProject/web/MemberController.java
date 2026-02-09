package spring.jwProject.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.jwProject.domain.address.Address;
import spring.jwProject.domain.member.Gender;
import spring.jwProject.domain.member.Member;
import spring.jwProject.repository.member.MemberRepository;
import spring.jwProject.sevice.AddressService;
import spring.jwProject.sevice.MemberService;
import spring.jwProject.validation.form.CheckPWMember;
import spring.jwProject.validation.form.LoginMember;
import spring.jwProject.validation.form.SignUpMember;
import spring.jwProject.validation.form.UpdateMember;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final AddressService addressService;

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

        Member login = memberService.login(member.getId(), member.getPassword());

        if (login == null) {
            bindingResult.reject("loginError", "로그인 에러메시지");
            return "member/login";
        }

        log.info("login id:{}, name:{}",login.getId(), login.getNo());
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(login.getId(), login.getName()));
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
        return memberService.checkId(id);
    }

    //회원가입
    @PostMapping("/signup")
    public String signUp(@Validated @ModelAttribute("member") SignUpMember signUpMember, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "member/signup";
        }

        Member signedMember = memberService.signUp(new Member(
                signUpMember.getId(),
                signUpMember.getEmail(),
                signUpMember.getPassword(),
                signUpMember.getName(),
                signUpMember.getGender(),
                signUpMember.getTelecom(),
                signUpMember.getPhoneNumber()));
        redirectAttributes.addAttribute("loginId", signedMember.getId());

        if (!signUpMember.getPostcode().isEmpty()) {
            addressService.save(new Address(signedMember,
                    "기본배송지",
                    signUpMember.getPostcode(),
                    signUpMember.getRoadAddress(),
                    signUpMember.getDetailAddress()));
        }

        log.info("signUp complete, Member={}",signUpMember);
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
        Member checkMember = memberService.login(checkPWMember.getId(), checkPWMember.getPassword());

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
    public String memberModifyForm(@RequestParam("memberId") String memberId, Model model, HttpServletRequest request) {

        if (!new LoginMember().loginCheck(request, model)) {
            return "redirect:/member/login?redirectURL=" + request.getRequestURI();
        }

        Member member = memberRepository.findById(memberId);
        UpdateMember updateMember = new UpdateMember(member.getId(),
                member.getName(),
                member.getEmail(),
                member.getPassword(),
                member.getTelecom(),
                member.getPhoneNumber(),
                member.getGender());
        model.addAttribute("updateMember", updateMember);

        return "member/member_modify";
    }

    @PostMapping("/mypage/memberModify")
    public String memberModify(@Validated @ModelAttribute("updateMember") UpdateMember updateMember, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            log.info("myPageUpdate Fail={}", bindingResult);
            return "member/member_modify";
        }

        //<변경된 필드명, 변경값>
        Map<String, Object> result = memberService.memberModify(updateMember);

        for (String key : result.keySet()) {
            if (key.equals("name")) {
                // 세션에 저장된 값 변경
                HttpSession session = request.getSession();
                LoginMember loginMember = (LoginMember) session.getAttribute(SessionConst.LOGIN_MEMBER);
                loginMember.setName((String) result.get(key));
            }
        }

        redirectAttributes.addFlashAttribute("result", result);
        redirectAttributes.addAttribute("memberId", updateMember.getId());
        return "redirect:/member/mypage/memberModify/complete";
    }


    @GetMapping("/mypage/memberModify/complete")
    public String memberModifyComplete(@ModelAttribute("result") Map<String, String> result, BindingResult bindingResult,
                                       @RequestParam("memberId") String memberId, Model model) {

        log.info("memberId={}, result={}", memberId, result);
        //변경한 것만 뜨게
        model.addAttribute("result", result);
        // 처음내용을 기억하고 있어야 함, 변경된 내용을
        return "member/member_modify_complete";
    }

/*
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
