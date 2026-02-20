package spring.jwProject.web;


import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.jwProject.domain.address.Address;
import spring.jwProject.domain.member.Gender;
import spring.jwProject.domain.member.Member;
import spring.jwProject.repository.member.MemberRepository;
import spring.jwProject.sevice.AddressService;
import spring.jwProject.sevice.MemberService;
import spring.jwProject.validation.form.LoginMember;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final AddressService addressService;

    /**
     * 메인 화면
     */
    @RequestMapping({"/", "/home"})
    public String home(HttpServletRequest request, Model model) {

        new LoginMember().loginCheck(request, model);

        return "home/home";
    }

    @GetMapping("/test")
    public String test() {
        return "item/item_detail";
    }


    //=====판매자 화면 연결(임시) 끝 =====//
    /**
     * 테스트 데이터
     */
    @PostConstruct
    public void init() {
        Member member1 = new Member("member1", "member1@Email.com", "password", "회원1", Gender.MAN, "SKT",
                "010-1111-1111");
        Member member2 = new Member("member2", "member2@Email.com", "password", "회원2", Gender.WOMAN, "KT",
                "010-2222-2222");

        Member member3 = new Member("test", "test@Email.com", "test", "test", Gender.WOMAN, "KT알뜰폰",
                "010-3333-3333");
        memberService.signUp(member1);
        memberService.signUp(member2);
        memberService.signUp(member3);


        Address a1 = new Address(member3, "주소1", "사람1", "010-1111-1111", "11111", "경기 성남시 분당구", "", "요청사항1", false);
        Address a2 = new Address(member3, "메인주소","22222", "경기 성남시 분당구", "", "사람2", "010-2222-2222");
        Address a3 = new Address(member3, "주소3", "사람3", "010-3333-3333", "33333", "경기 성남시 분당구", "303호", "", false);
        Address a4 = new Address(member1, "주소4", "사람4", "010-4444-4444", "44444", "경기 성남시 분당구", "404호", "요청사항4", false);
        addressService.signUp(a2);
        addressService.save(a1);
        addressService.save(a3);
        addressService.save(a4);
    }

}
