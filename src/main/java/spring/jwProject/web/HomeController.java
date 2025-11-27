package spring.jwProject.web;


import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.jwProject.domain.member.Member;
import spring.jwProject.repository.member.MemoryMemberRepository;

import static spring.jwProject.web.SessionConst.LOGIN_MEMBER;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemoryMemberRepository repository;

    /**
     * 메인 화면
     */
    @RequestMapping({"/", "/home"})
    public String home(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return "home/home";
        }

        Member loginMember = (Member) session.getAttribute(LOGIN_MEMBER);

        if (loginMember == null) {
            return "home/home";
        }


        model.addAttribute("member", loginMember);
        model.addAttribute("isLogin", true);
        return "home/home";
    }

    /**
     * 테스트 데이터
     */
    @PostConstruct
    public void init() {
        repository.init();
    }

}
