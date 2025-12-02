package spring.jwProject.domain.member;

import lombok.Data;

@Data
public class Member {
    public Long memberNo;
    public String memberId;
    public String memberName;
    public String password;
    public MemberLevel grade;


    public String postcode;
    public String roadAddress;
    public String jibunAddress;
    public String detailAddress;
    public String extraAddress;

    public Member() {
    }

    public Member(String memberId, String memberName, String password, String address) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.password = password;
    }

    public Member(String memberId, String memberName, String password, MemberLevel grade) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.password = password;
        this.grade = grade;
    }
}
