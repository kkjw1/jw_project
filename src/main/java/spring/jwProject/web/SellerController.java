package spring.jwProject.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.jwProject.domain.seller.Seller;
import spring.jwProject.repository.seller.SellerRepository;
import spring.jwProject.sevice.SellerService;
import spring.jwProject.validation.form.LoginMember;
import spring.jwProject.validation.form.LoginSeller;
import spring.jwProject.validation.form.SignUpSeller;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {

    public final SellerRepository repository;
    public final SellerService service;

    //회원가입 페이지
    @GetMapping("/signup")
    public String signUpForm(Model model) {
        model.addAttribute("signUpSeller", new SignUpSeller());
        return "seller/signup";
    }

    //회원가입
    @PostMapping("/signup")
    public String signUp(@Validated @ModelAttribute("signUpSeller") SignUpSeller signUpSeller, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("seller signup Error");
            return "seller/signup";
        }

        log.info("회원가입: {}", signUpSeller.getName());

        service.signUp(signUpSeller);
        redirectAttributes.addAttribute("id", signUpSeller.getId());
        return "redirect:/seller/login/{id}";
    }

    //아이디 중복 체크
    @PostMapping("/signup/checkId")
    @ResponseBody
    public boolean signupCheckId(@RequestParam("sellerId") String sellerId) {
        return service.checkId(sellerId);
    }

    //로그인 화면
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginSeller",new LoginSeller());
        return "seller/login";
    }

    //회원가입->로그인 화면 /seller/login
    @GetMapping("/login/{id}")
    public String loginForm2(@PathVariable("id") String id, Model model) {
        LoginSeller loginSeller = new LoginSeller();
        loginSeller.setId(id);
        model.addAttribute("loginSeller", loginSeller);
        return "seller/login";
    }

    //로그인
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginSeller") LoginSeller loginSeller, BindingResult bindingResult,
                        HttpServletRequest request, Model model) {
        if (bindingResult.hasErrors()) {
            log.info("로그인 에러");
            return "seller/login";
        }

        Seller login = service.login(loginSeller);
        if (login == null) {
            bindingResult.reject("loginError", "로그인 에러 메시지");
            return "seller/login";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_SELLER, loginSeller);

        return "seller/home";
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

}
