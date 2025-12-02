package spring.jwProject.validation.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import spring.jwProject.domain.member.MemberLevel;

@Data
public class SignUpMember {

    @NotBlank(message = "아이디가 공백입니다.")
    public String memberId;

    @NotBlank(message = "이름이 공백입니다.")
    public String memberName;

    @NotNull(message = "비밀번호가 공백입니다.")
    @Size(min = 8, message = "비밀번호는 8자리 이상이여야 합니다.")
    public String password;

    @NotBlank(message = "우편번호가 공백입니다.")
    public String postcode;

    public String roadAddress;
    public String jibunAddress;
    public String detailAddress;
    public String extraAddress;
    public MemberLevel grade;

    public SignUpMember() {

    }
}
