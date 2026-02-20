package spring.jwProject.validation.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginSeller {
    @NotBlank(message = "아이디가 공백입니다.")
    String id;

    @NotBlank(message = "비밀번호가 공백입니다.")
    String password;

    public LoginSeller() {
    }

    public LoginSeller(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
