package spring.jwProject.domain;

import lombok.Data;

@Data

public class BeforeMember {
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

    public BeforeMember() {
    }

    public BeforeMember(String memberId, String memberName, String password) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.password = password;
    }

    public BeforeMember(String memberId, String memberName, String password, MemberLevel grade) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.password = password;
        this.grade = grade;
    }
}