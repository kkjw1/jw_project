package spring.jwProject.validation.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import spring.jwProject.domain.member.Gender;
import spring.jwProject.domain.member.MemberLevel;

@Data
public class SignUpMember {

    @NotBlank(message = "아이디가 공백입니다.")
    private String id;

    @NotNull(message = "비밀번호가 공백입니다.")
    @Size(min = 8, message = "비밀번호는 8자리 이상이여야 합니다.")
    private String password;

    @Email(message = "이메일 주소가 정확한지 확인해 주세요.")
    private String email;

    @NotBlank(message = "이름이 공백입니다.")
    private String name;

    @NotNull(message = "통신사가 공백입니다.")
    private String telecom;

    private Gender gender;

    @NotNull(message = "휴대전화번호가 공백입니다.")
    private String phoneNumber;

    private String postcode;
    private String roadAddress;
    private String detailAddress;

    public SignUpMember() {

    }
}
