package spring.jwProject.validation.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

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
}
