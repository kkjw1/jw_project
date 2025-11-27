package spring.jwProject.domain.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Member {
    public Long memberNo;
    public String memberId;
    public String memberName;
    public String password;
    public String address;

    public MemberLevel grade;

    public Member() {
    }

    public Member(String memberId, String memberName, String password, String address) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.password = password;
        this.address = address;
    }

    public Member(String memberId, String memberName, String password, String address, MemberLevel grade) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.password = password;
        this.address = address;
        this.grade = grade;
    }
}
