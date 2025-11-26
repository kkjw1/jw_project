package spring.jwProject.web;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.jwProject.repository.member.MemoryMemberRepository;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemoryMemberRepository repository;


    @RequestMapping({"/", "/home"})
    public String home() {
        return "/home/home";
    }

    /**
     * 테스트 데이터
     */
    @PostConstruct
    public void init() {
        repository.init();
    }

}
