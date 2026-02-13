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
import spring.jwProject.domain.address.Address;
import spring.jwProject.domain.member.Member;
import spring.jwProject.repository.member.MemberRepository;
import spring.jwProject.sevice.AddressService;
import spring.jwProject.sevice.MemberService;
import spring.jwProject.validation.form.*;

import java.util.ArrayList;
import java.util.Collections;
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

        log.info("login id:{}, name:{}",login.getId(), login.getName());
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginMember(login.getId(), login.getName()));
        return "redirect:/";
    }


    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            LoginMember loginMember = (LoginMember) session.getAttribute(SessionConst.LOGIN_MEMBER);
            log.info("logout id:{}, name:{}", loginMember.getId(), loginMember.getName());
            session.invalidate();
//            session.removeAttribute(SessionConst.LOGIN_MEMBER);
        }
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
            addressService.signUp(new Address(signedMember,
                    "기본배송지",
                    signUpMember.getPostcode(),
                    signUpMember.getRoadAddress(),
                    signUpMember.getDetailAddress(),
                    signUpMember.getName(),
                    signUpMember.getPhoneNumber()));
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
    public String memberModifyCompleteForm(@ModelAttribute("result") Map<String, String> result, BindingResult bindingResult,
                                           @RequestParam("memberId") String memberId, Model model) {

        log.info("memberId={}, result={}", memberId, result);
        model.addAttribute("result", result);
        return "member/member_modify_complete";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("memberId") String memberId, HttpServletRequest request) {
        boolean result = memberService.withdraw(memberId);

        if (result) {
            log.info("delete member={}", memberId);
            HttpSession session = request.getSession(false);
            session.invalidate();
            return "redirect:/";
        }

        log.info("delete fail memberId={}", memberId);
        return "redirect:/";
    }


    //배송지 관리 페이지, /member/mypage/addressManage?memberId=test
    @GetMapping("/mypage/addressManage")
    public String addressForm(@RequestParam("memberId") String memberId, HttpServletRequest request,Model model) {
        if (!new LoginMember().loginCheck(request, model)) {
            return "redirect:/member/login?redirectURL=" + request.getRequestURI();
        }
        //주소 리스트 출력 기능
        List<Address> addresses = addressService.getAddresses(memberId);
        if (addresses.isEmpty()) {
            model.addAttribute("addresses", Collections.emptyList());
        } else {
            Address mainAddress = addresses.get(0);
            List<Address> addressesList = addresses.subList(1, addresses.size());
            model.addAttribute("mainAddress", mainAddress);
            model.addAttribute("addresses", addressesList);
        }
        return "member/address_manage";
    }


    //배송지 추가 페이지
    @GetMapping("/mypage/addressManage/add")
    public String addressAddForm(HttpServletRequest request, Model model) {
        if (!new LoginMember().loginCheck(request, model)) {
            return "redirect:/member/login?redirectURL=" + request.getRequestURI();
        }
        model.addAttribute("manageAddress", new ManageAddress());
        return "member/address_manage_add";
    }


    //배송지 추가
    @PostMapping("/mypage/addressManage/add")
    public String addressAdd(@Validated @ModelAttribute("manageAddress") ManageAddress manageAddress, BindingResult bindingResult,
                             @RequestParam("memberId") String memberId, RedirectAttributes redirectAttributes,
                             HttpServletRequest request, Model model) {

        if (bindingResult.hasErrors()) {
            new LoginMember().loginCheck(request, model);
            return "member/address_manage_add";
        }

        manageAddress.setMemberId(memberId);
        log.info("manageAddress.mainAddress={}", manageAddress.getMainAddress());
        addressService.save(manageAddress);

        redirectAttributes.addAttribute("memberId", memberId);
        return "redirect:/member/mypage/addressManage";
    }

    //내정보 가져오기(배송지 추가 페이지)
    @GetMapping("mypage/addressManage/add/getMyInfo")
    @ResponseBody
    public List<String> getMyInfo(HttpServletRequest request) {
        LoginMember loginMember = (LoginMember)request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
        log.info("getMyInfo id={}", loginMember.getId());
        Member member = memberRepository.findById(loginMember.getId());
        List<String> list = new ArrayList<>();
        list.add(member.getName());
        list.add(member.getPhoneNumber());
        return list;
    }


    //주문 목록
    @GetMapping("/mypage/orderList")
    public String orderListForm(Model model) {
        return "member/order_list";
    }

    //찜한 상품
    @GetMapping("/mypage/wishlist")
    public String wishlistForm(Model model) {
        return "member/wishlist";
    }

    //취소 반품 교환 내역
    @GetMapping("/mypage/exchange")
    public String exchangeForm(Model model) {
        return "member/exchange";
    }
}
