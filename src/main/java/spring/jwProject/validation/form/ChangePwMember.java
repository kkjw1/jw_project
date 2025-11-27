package spring.jwProject.validation.form;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePwMember {

    @NotBlank(message = "비밀번호가 공백입니다.")
    @Size(min = 8, message = "비밀번호는 8자리 이상이여야 합니다.")
    public String password;


    @NotBlank(message = "비밀번호가 공백입니다.")
    @Size(min = 8, message = "비밀번호는 8자리 이상이여야 합니다.")
    public String password2;
}
