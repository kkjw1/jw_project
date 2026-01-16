package spring.jwProject.validation.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import spring.jwProject.domain.member.Gender;

@Data
public class UpdateMember {

    public String id;

    @NotBlank(message = "이름이 공백입니다.")
    private String name;

    @Email(message = "이메일 주소가 정확한지 확인해 주세요.")
    private String email;

    private String password;
    private String passwordCheck;

    @NotNull(message = "통신사가 공백입니다.")
    private String telecom;


    @NotNull(message = "휴대전화번호가 공백입니다.")
    private String phoneNumber;

    private Gender gender;


    public UpdateMember() {
    }

    public UpdateMember(String id, String name, String email, String password, String telecom, String phoneNumber, Gender gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.telecom = telecom;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }
}
