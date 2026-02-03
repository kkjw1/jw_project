package spring.jwProject.validation.form;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.ui.Model;

import static spring.jwProject.web.SessionConst.LOGIN_MEMBER;

@Data
public class LoginMember {

    @NotBlank(message = "아이디가 공백입니다.")
    String id;

    @NotBlank(message = "비밀번호가 공백입니다.")
    String password;

    String name;

    public LoginMember() {

    }

    public LoginMember(String id) {
        this.id = this.id;
    }

    public LoginMember(String id, String name) {
        this.id = id;
        this.name = name;
    }


    /**
     * @param request
     * @param model
     * @return true, false
     */
    public boolean loginCheck(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return false;
        }

        LoginMember loginMember = (LoginMember) session.getAttribute(LOGIN_MEMBER);

        if (loginMember == null) {
            model.addAttribute("isLogin", false);
            return false;
        }


        model.addAttribute("member", loginMember);
        model.addAttribute("isLogin", true);
        return true;
    }
}
