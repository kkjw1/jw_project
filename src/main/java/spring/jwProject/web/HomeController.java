package spring.jwProject.web;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.jwProject.repository.member.MemberRepository;
import spring.jwProject.repository.member.MemoryMemberRepository;
import spring.jwProject.sevice.MemberService;

@Controller
public class HomeController {

    private final MemoryMemberRepository repository;

    public HomeController(MemoryMemberRepository repository) {
        this.repository = repository;
        this.repository.init();
    }

    @RequestMapping({"/", "/home"})
    public String home() {
        return "/home";
    }

    @RequestMapping("/login")
    public String login() {
        return "/login";
    }
}
