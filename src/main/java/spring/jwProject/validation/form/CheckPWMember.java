package spring.jwProject.validation.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CheckPWMember {
    private String id;
    @NotBlank(message = "비밀번호가 공백입니다.")
    private String password;

    public CheckPWMember() {
    }

    public CheckPWMember(String id) {
        this.id = id;
    }
}
