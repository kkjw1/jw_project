package spring.jwProject.web;


import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.jwProject.domain.member.Member;
import spring.jwProject.repository.member.MemberRepository;
import spring.jwProject.validation.form.LoginMember;

import static spring.jwProject.web.SessionConst.LOGIN_MEMBER;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository repository;

    /**
     * 메인 화면
     */
    @RequestMapping({"/", "/home"})
    public String home(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return "home/home";
        }

        LoginMember loginMember = (LoginMember) session.getAttribute(LOGIN_MEMBER);

        if (loginMember == null) {
            model.addAttribute("isLogin", false);
            return "home/home";
        }


        model.addAttribute("member", loginMember);
        model.addAttribute("isLogin", true);
        return "home/home";
    }

    //todo 기능 만든 후 삭제해야 함
    //=====판매자 화면 연결(임시)시작 =====//
    @GetMapping("/seller")
    public String sellerHomeForm() {
        return "seller/home";
    }

    @GetMapping("/item_manage")
    public String item_manage() {
        return "seller/item_manage";
    }


    @GetMapping("/order_delivery")
    public String order_delivery() {
        return "seller/order_delivery";
    }

    @GetMapping("/inquiry_manage")
    public String inquiry_manage() {
        return "seller/inquiry_manage";
    }

    @GetMapping("/item_new")
    public String item_new() {
        return "seller/item_new";
    }


    @GetMapping("/inquiry_reply")
    public String inquiry_reply() {
        return "seller/inquiry_reply";
    }

    @GetMapping("/order_delivery_detail")
    public String order_delivery_detail() {
        return "seller/order_delivery_detail";
    }

    @GetMapping("/test")
    public String test() {
        return "item/item_detail";
    }


    //=====판매자 화면 연결(임시) 끝 =====//
    /**
     * 테스트 데이터
     */
/*
    @PostConstruct
    public void init() {
        repository.init();
    }
*/

}
