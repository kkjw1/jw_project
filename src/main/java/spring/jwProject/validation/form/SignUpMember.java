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

    @NotBlank(message = "주소가 공백입니다.")
    public String address;

    public MemberLevel grade;

    public SignUpMember() {
    }

    public SignUpMember(String memberId, String memberName, String password, String address) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.password = password;
        this.address = address;
    }

    public SignUpMember(String memberId, String memberName, String password, String address, MemberLevel grade) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.password = password;
        this.address = address;
        this.grade = grade;
    }
}
